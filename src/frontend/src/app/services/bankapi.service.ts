import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CustomerDto} from "../model/customer-dto";
import {Observable, throwError} from "rxjs";
import {catchError} from "rxjs/operators";
import {ApiExceptionPayload} from "../model/api-exception-payload";

@Injectable({
  providedIn: 'root'
})
export class BankapiService {


  constructor(private httpClient: HttpClient) { }

  saveCustomer(customerDto: CustomerDto): Observable<any> {
    return this.httpClient.post("http://localhost:8080/api/v1/customers/save", customerDto, {responseType: "text"}).pipe(
      catchError((err) => {
        const apiPayload: ApiExceptionPayload = JSON.parse(err.error);
        console.log(apiPayload)
        return throwError(() => new Error(apiPayload.message));
      })
    );
  }
}
