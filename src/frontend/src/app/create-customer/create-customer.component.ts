import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ValidationErrors, Validators} from "@angular/forms";
import {Observable, Observer} from "rxjs";
import {CustomerService} from "../services/customer.service";
import {CustomerDto} from "../model/customer-dto";
import {NzNotificationService} from "ng-zorro-antd/notification";
import {NzMessageService} from "ng-zorro-antd/message";

@Component({
  selector: 'app-create-customer',
  templateUrl: './create-customer.component.html',
  styleUrls: ['./create-customer.component.scss']
})
export class CreateCustomerComponent implements OnInit {

  validateForm: FormGroup;
  enabled: boolean = true;
  errorMessage?: string;
  error?: boolean;

  constructor(private fb: FormBuilder, private bankapiService: CustomerService, private notification: NzNotificationService, private message: NzMessageService) {
    this.validateForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      surname: ['', [Validators.required, Validators.minLength(2)]],
      gender: ['', [Validators.required]],
      age: ['', [Validators.required]],
      identification: ['', [Validators.required, Validators.minLength(7)],[this.userIdentificationAsyncValidator]],
      address: ['', [Validators.required]],
      phoneNumber: ['', [Validators.required]],
      password: ['', [Validators.required]],
      confirm: ['', [this.confirmValidator]],
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

  validateConfirmPassword(): void {
    setTimeout(() => this.validateForm.controls['confirm'].updateValueAndValidity());
  }


  // eslint-disable-next-line @typescript-eslint/explicit-function-return-type
  userIdentificationAsyncValidator = (control: FormControl) =>
    new Observable((observer: Observer<ValidationErrors | null>) => {
      this.error = true;
      this.bankapiService.getCustomerByIdentification(control.value).subscribe({
        next: (customerDto) => {
          this.error = true;
        },
        error: (e) => {
          this.error = false;
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


  confirmValidator = (control: FormControl): { [s: string]: boolean } => {
    if (!control.value) {
      return {error: true, required: true};
    } else if (control.value !== this.validateForm.controls['password'].value) {
      return {confirm: true, error: true};
    }
    return {};
  };

  submitForm(): void {
    const customerDto: CustomerDto = {
      age: this.validateForm.value['age'],
      address: this.validateForm.value['address'],
      gender: this.validateForm.value['gender'],
      identification: this.validateForm.value['identification'],
      name: this.validateForm.value['name'],
      password: this.validateForm.value['password'],
      phoneNumber: this.validateForm.value['phoneNumber'],
      state: this.validateForm.value['state'],
      surname: this.validateForm.value['surname']
    }

    this.bankapiService.saveCustomer(customerDto).subscribe({
      next: (message) => {
        console.log(message);
        this.notification.blank('Customer created', message, {nzPlacement: "bottomRight"});
        this.resetForm(new MouseEvent('click'));
      },
      error: (e) => {
        this.message.create("error", e);
      }
    })
  }


}
