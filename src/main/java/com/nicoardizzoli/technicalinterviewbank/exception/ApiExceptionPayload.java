package com.nicoardizzoli.technicalinterviewbank.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiExceptionPayload {
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Throwable throwable;
    private HttpStatus httpStatus;
    private ZonedDateTime zonedDateTime;

    //TODO: hacer el showThrowable configurable por sistema
    public ApiExceptionPayload(String message, Throwable throwable, HttpStatus httpStatus, ZonedDateTime zonedDateTime, Boolean showThrowable) {
        this.message = message;
        if (showThrowable) {
            this.throwable = throwable;
        }
        this.httpStatus = httpStatus;
        this.zonedDateTime = zonedDateTime;
    }
}
