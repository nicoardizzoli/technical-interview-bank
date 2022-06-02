import { Injectable } from '@angular/core';
import {CustomerDto} from "../model/customer-dto";
import {Observable, throwError} from "rxjs";
import {catchError} from "rxjs/operators";
import {ApiExceptionPayload} from "../model/api-exception-payload";
import {HttpClient} from "@angular/common/http";
import {MovementDto} from "../model/movement-dto";

@Injectable({
  providedIn: 'root'
})
export class MovementService {

  constructor(private httpClient: HttpClient) { }

  saveMovement(movementDto: MovementDto): Observable<any> {
    return this.httpClient.post("http://localhost:8080/api/v1/movements/save", movementDto, {responseType: "text"})
      .pipe(
        catchError((err) => {
          const apiPayload: ApiExceptionPayload = JSON.parse(err.error);
          return throwError(() => new Error(apiPayload.message));
        })
      );
  }
}
