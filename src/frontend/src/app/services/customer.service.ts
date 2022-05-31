import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CustomerDto} from "../model/customer-dto";
import {Observable, throwError} from "rxjs";
import {catchError} from "rxjs/operators";
import {ApiExceptionPayload} from "../model/api-exception-payload";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {


  constructor(private httpClient: HttpClient) {
  }

  saveCustomer(customerDto: CustomerDto): Observable<any> {
    return this.httpClient.post("http://localhost:8080/api/v1/customers/save", customerDto, {responseType: "text"})
      .pipe(
        catchError((err) => {
          const apiPayload: ApiExceptionPayload = JSON.parse(err.error);
          return throwError(() => new Error(apiPayload.message));
        })
      );
  }

  getCustomerByPhoneNumber(phoneNumber: string): Observable<CustomerDto> {
    return this.httpClient.get<CustomerDto>("http://localhost:8080/api/v1/customers/phoneNumber/" + phoneNumber)
      .pipe(
        catchError((err) => {
          return throwError(() => new Error(err.error.message));
        })
      );
  }

  getCustomerByIdentification(identification: string): Observable<CustomerDto> {
    return this.httpClient.get<CustomerDto>("http://localhost:8080/api/v1/customers/identification/" + identification)
      .pipe(
        catchError((err) => {
          return throwError(() => new Error(err.error.message));
        })
      );
  }

  getAllCustomers(): Observable<Array<CustomerDto>> {
    return this.httpClient.get<Array<CustomerDto>>("http://localhost:8080/api/v1/customers")
      .pipe(
        catchError((err) => {
          return throwError(() => new Error(err.error.message));
        })
      );
  }
}
