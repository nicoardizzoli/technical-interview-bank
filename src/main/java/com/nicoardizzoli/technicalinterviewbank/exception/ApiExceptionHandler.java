package com.nicoardizzoli.technicalinterviewbank.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * SIEMPRE PARA MANEJAR LAS EXCEPTIONS ES BUENO SABER CUALES HTTPSTATUS USAR YA QUE MUCHAS VECES SE MANEJAN MAL LAS RESPONSES
 *  HTTP STATUS CODES //
 *  Informational responses (100-199)
 *  Successfull responses (200-299)
 *  Redirects (300 - 399)
 *  client errors (400-499)
 * server errors (500-599)
 * de todas maneras, podemos entrar en la clase HttpStatus y ver el que mas se adapte a la respuesta que queremos dar.
 */

@ControllerAdvice
public class ApiExceptionHandler {

    @Value("${api.throwable-in-custom-exception}")
    Boolean showThrowableException;

    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){
        ApiExceptionPayload apiExceptionPayload = new ApiExceptionPayload(e.getMessage(), e, HttpStatus.BAD_REQUEST, ZonedDateTime.now(),showThrowableException);
        return new ResponseEntity<>(apiExceptionPayload, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e){
        ApiExceptionPayload apiExceptionPayload = new ApiExceptionPayload(e.getMessage(), e, HttpStatus.BAD_REQUEST, ZonedDateTime.now(),showThrowableException);
        return new ResponseEntity<>(apiExceptionPayload, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleClienteExistenteException(MethodArgumentNotValidException e) {
        ApiExceptionPayload apiExceptionPayload = new ApiExceptionPayload(Objects.requireNonNull(e.getFieldError()).getDefaultMessage(), e, HttpStatus.BAD_REQUEST, ZonedDateTime.now(),showThrowableException);
        return new ResponseEntity<>(apiExceptionPayload, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FoundException.class)
    public ResponseEntity<Object> handleClienteExistenteException(FoundException e) {
        ApiExceptionPayload apiExceptionPayload = new ApiExceptionPayload(Objects.requireNonNull(e.getMessage()), e, HttpStatus.NOT_ACCEPTABLE, ZonedDateTime.now(),showThrowableException);
        return new ResponseEntity<>(apiExceptionPayload, HttpStatus.NOT_ACCEPTABLE);
    }
}
