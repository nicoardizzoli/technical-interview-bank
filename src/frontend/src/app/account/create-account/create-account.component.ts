import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ValidationErrors, Validators} from "@angular/forms";
import {CustomerService} from "../../services/customer.service";
import {NzNotificationService} from "ng-zorro-antd/notification";
import {NzMessageService} from "ng-zorro-antd/message";
import {Observable, Observer} from "rxjs";
import {CustomerDto} from "../../model/customer-dto";
import {AccountDto} from "../../model/account-dto";
import {AccountService} from "../../services/account.service";

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.scss']
})
export class CreateAccountComponent implements OnInit {

  validateForm: FormGroup;
  enabled: boolean = true;
  errorMessage?: string;
  error?: boolean;
  renderCustomerData: boolean = false;
  customerDto!: CustomerDto;

  constructor(private fb: FormBuilder, private customerService: CustomerService, private notification: NzNotificationService, private message: NzMessageService, private accountService: AccountService) {
    this.validateForm = this.fb.group({
      accountType: ['', [Validators.required, Validators.minLength(2)]],
      balance: ['', [Validators.required]],
      withdrawLimit: ['', [Validators.required]],
      identification: ['', [Validators.required, Validators.minLength(7)], [this.customerIdentificationAsyncValidator]],
      state: ['true', [Validators.required]]
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

  customerIdentificationAsyncValidator = (control: FormControl) =>
    new Observable((observer: Observer<ValidationErrors | null>) => {
      this.error = true;
      this.customerService.getCustomerByIdentification(control.value).subscribe({
        next: (customerDto) => {
          this.error = false;
          this.customerDto = customerDto;
          this.renderCustomerData = true;
        },
        error: (e) => {
          this.error = true;
          this.customerDto.customerId = "";
          this.renderCustomerData = false;
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
    const accountDto: AccountDto = {
      accountType: this.validateForm.value['accountType'],
      balance: this.validateForm.value['balance'],
      state: this.validateForm.value['state'],
      withdrawLimit: this.validateForm.value['withdrawLimit'],
      customerIdentification: this.validateForm.value['identification']
    }

    this.accountService.saveAccount(accountDto).subscribe({
      next: (message) => {
        console.log(message);
        this.notification.blank('Account created', message, {nzPlacement: "bottomRight"});
        this.resetForm(new MouseEvent('click'));
        this.renderCustomerData = false;
        this.customerDto.customerId = "";
      },
      error: (e) => {
        this.message.create("error", e);
      }
    })
  }

}
