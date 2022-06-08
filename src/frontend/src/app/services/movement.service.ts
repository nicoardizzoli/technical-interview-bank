import {Injectable} from '@angular/core';
import {Observable, throwError} from "rxjs";
import {catchError} from "rxjs/operators";
import {ApiExceptionPayload} from "../model/api-exception-payload";
import {HttpClient, HttpParams} from "@angular/common/http";
import {MovementDto} from "../model/movement-dto";
import {MovementReportDto} from "../model/movement-report-dto";
import {DatePipe} from "@angular/common";

@Injectable({
  providedIn: 'root'
})
export class MovementService {

  constructor(private httpClient: HttpClient,private datePipe: DatePipe) { }

  saveMovement(movementDto: MovementDto): Observable<any> {
    return this.httpClient.post("http://localhost:8080/api/v1/movements/save", movementDto, {responseType: "text"})
      .pipe(
        catchError((err) => {
          const apiPayload: ApiExceptionPayload = JSON.parse(err.error);
          return throwError(() => new Error(apiPayload.message));
        })
      );
  }

  completeReport(customerIdentification: string, startDate: Date, endDate: Date) {
    const startDateFormatted = this.datePipe.transform(startDate, "yyyy-MM-dd'T'HH:mm:ss")!;
    const endDateFormatted = this.datePipe.transform(endDate, "yyyy-MM-dd'T'HH:mm:ss")!;
    let params = new HttpParams();
    params = params.append('startDate', startDateFormatted);
    params = params.append('endDate', endDateFormatted);

    return this.httpClient.get<Array<MovementReportDto>>("http://localhost:8080/api/v1/movements/complete-report/"+customerIdentification, {params: params})
      .pipe(
        catchError((err) => {
          const apiPayload: ApiExceptionPayload = JSON.parse(err.error);
          return throwError(() => new Error(apiPayload.message));
        })
      );
  }
}
