import { Injectable } from '@angular/core';
import {CustomerDto} from "../model/customer-dto";
import {Observable, throwError} from "rxjs";
import {catchError} from "rxjs/operators";
import {ApiExceptionPayload} from "../model/api-exception-payload";
import {HttpClient} from "@angular/common/http";
import {AccountDto} from "../model/account-dto";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  url : string = environment.serviceUrl;

  constructor(private httpClient: HttpClient) {
  }


  saveAccount(accountDto: AccountDto): Observable<any> {

    return this.httpClient.post(this.url+"/api/v1/accounts/save", accountDto, {responseType: "text"})
      .pipe(
        catchError((err) => {
          const apiPayload: ApiExceptionPayload = JSON.parse(err.error);
          return throwError(() => new Error(apiPayload.message));
        })
      );
  }

  getAllAccounts(): Observable<Array<AccountDto>> {
    return this.httpClient.get<Array<AccountDto>>(this.url+"/api/v1/accounts")
      .pipe(
        catchError((err) => {
          return throwError(() => new Error(err.error.message));
        })
      );
  }

  getAccountByAccountNumber(accountNumber: number): Observable<AccountDto> {
    return this.httpClient.get<AccountDto>(this.url+"/api/v1/accounts/"+accountNumber)
      .pipe(
        catchError((err) => {
          return throwError(() => new Error(err.error.message));
        })
      );
  }
}
