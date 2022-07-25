import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ValidationErrors, Validators} from "@angular/forms";
import {NzNotificationService} from "ng-zorro-antd/notification";
import {NzMessageService} from "ng-zorro-antd/message";
import {AccountService} from "../../services/account.service";
import {Observable, Observer} from "rxjs";
import {AccountDto} from "../../model/account-dto";
import {MovementDto} from "../../model/movement-dto";
import {MovementService} from "../../services/movement.service";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-create-movement',
  templateUrl: './create-movement.component.html',
  styleUrls: ['./create-movement.component.scss']
})
export class CreateMovementComponent implements OnInit {

  validateForm: FormGroup;
  enabled: boolean = true;
  errorMessage?: string;
  error?: boolean;
  renderAccountData: boolean = false;
  accountDto!: AccountDto;

  constructor(private datePipe: DatePipe, private fb: FormBuilder, private accountService: AccountService, private notification: NzNotificationService, private message: NzMessageService, private movementService: MovementService) {
    this.validateForm = this.fb.group({
      accountNumber: ['', [Validators.required, Validators.minLength(7)], [this.accountNumberAsyncValidator]],
      movementType: ['', [Validators.required, Validators.minLength(2)]],
      amount: ['', [Validators.required]],
    });
  }

  ngOnInit(): void {
  }

  resetForm(e: MouseEvent): void {
    e.preventDefault();
    this.validateForm.reset();
    for (const key in this.validateForm.controls) {
      if (this.validateForm.controls.hasOwnProperty(key)) {
        this.validateForm.controls[key].markAsPristine();
        this.validateForm.controls[key].updateValueAndValidity();
      }
    }
  }

  accountNumberAsyncValidator = (control: FormControl) =>
    new Observable((observer: Observer<ValidationErrors | null>) => {
      this.error = true;
      this.accountService.getAccountByAccountNumber(control.value).subscribe({
        next: (accountDto) => {
          this.error = false;
          this.accountDto = accountDto;
          this.renderAccountData = true;
        },
        error: (e) => {
          this.error = true;
          this.renderAccountData = false;
        }
      });
      setTimeout(() => {

        if (this.error) {
          // you have to return `{error: true}` to mark it as an error event
          observer.next({error: true, duplicated: true});
        } else {
          observer.next(null);
        }


        observer.complete();
      }, 1000);
    });

  submitForm(): void {
    const movementDto: MovementDto = {
      accountNumber: this.validateForm.value['accountNumber'],
      date: this.datePipe.transform(new Date(), "yyyy-MM-dd'T'HH:mm:ss")!,
      movementType: this.validateForm.value['movementType'],
      amount: this.validateForm.value['amount']
    }

    this.movementService.saveMovement(movementDto).subscribe({
      next: (message: string) => {
        console.log(message);
        this.notification.blank('Movement created', message, {nzPlacement: "topRight"});
        this.resetForm(new MouseEvent('click'));
        this.renderAccountData = false;
      },
      error: (e) => {
        this.message.create("error", e);
      }
    })
  }

}
