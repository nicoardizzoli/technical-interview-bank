import {HttpStatusCode} from "@angular/common/http";

export interface ApiExceptionPayload {
    message:string;
    httpStatus: HttpStatusCode;
    zonedDateTime: Date;
}
