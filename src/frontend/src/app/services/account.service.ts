import { Injectable } from '@angular/core';
import {CustomerDto} from "../model/customer-dto";
import {Observable, throwError} from "rxjs";
import {catchError} from "rxjs/operators";
import {ApiExceptionPayload} from "../model/api-exception-payload";
import {HttpClient} from "@angular/common/http";
import {AccountDto} from "../model/account-dto";

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private httpClient: HttpClient) { }


  saveAccount(accountDto: AccountDto): Observable<any> {
    return this.httpClient.post("http://localhost:8080/api/v1/accounts/save", accountDto, {responseType: "text"})
      .pipe(
        catchError((err) => {
          const apiPayload: ApiExceptionPayload = JSON.parse(err.error);
          return throwError(() => new Error(apiPayload.message));
        })
      );
  }

  getAllAccounts(): Observable<Array<AccountDto>> {
    return this.httpClient.get<Array<AccountDto>>("http://localhost:8080/api/v1/accounts")
      .pipe(
        catchError((err) => {
          return throwError(() => new Error(err.error.message));
        })
      );
  }

  getAccountByAccountNumber(accountNumber: number): Observable<AccountDto> {
    return this.httpClient.get<AccountDto>("http://localhost:8080/api/v1/accounts/"+accountNumber)
      .pipe(
        catchError((err) => {
          return throwError(() => new Error(err.error.message));
        })
      );
  }
}
