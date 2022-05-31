import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest,} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {ApiExceptionPayload} from "../model/api-exception-payload";


@Injectable()
export class HttpErrorsInterceptor implements HttpInterceptor {
  constructor() {}

  intercept( req: HttpRequest<any>,next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((err) => {
        const apiPayload: ApiExceptionPayload = JSON.parse(err.error);
        console.log(apiPayload)
        return throwError(() => new Error(apiPayload.message));
      })
    );
  }
}
