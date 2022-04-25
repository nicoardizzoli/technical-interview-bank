package com.nicoardizzoli.pruebatecnicabanco.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){
        ApiExceptionPayload apiExceptionPayload = new ApiExceptionPayload(e.getMessage(), e, HttpStatus.BAD_REQUEST, ZonedDateTime.now());
        return new ResponseEntity<>(apiExceptionPayload, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e){
        ApiExceptionPayload apiExceptionPayload = new ApiExceptionPayload(e.getMessage(), e, HttpStatus.BAD_REQUEST, ZonedDateTime.now());
        return new ResponseEntity<>(apiExceptionPayload, HttpStatus.BAD_REQUEST);
    }

    /**
     * Este metodo maneja las excepciones que tira el @Valid en el controller, osea la validacion de los campos requeridos!
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ApiExceptionPayload apiExceptionPayload = new ApiExceptionPayload(Objects.requireNonNull(e.getFieldError()).getDefaultMessage(), e, HttpStatus.BAD_REQUEST, ZonedDateTime.now());
        return new ResponseEntity<>(apiExceptionPayload, HttpStatus.BAD_REQUEST);
    }
}
