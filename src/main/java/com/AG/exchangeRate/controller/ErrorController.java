package com.AG.exchangeRate.controller;

import com.AG.exchangeRate.model.errors.ErrorMessage;
import com.AG.exchangeRate.model.errors.ErrorMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * @author Rose
 */
@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(RestClientException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessageResponse handleRestClientException(final RestClientException ex) {
        return ErrorMessageResponse.builder().status(INTERNAL_SERVER_ERROR.value())
                .description("Internal error fetching a resource")
                .timestamp(new Date())
                .build();
    }

    @ExceptionHandler(ErrorMessage.class)
    public ResponseEntity<ErrorMessageResponse> ErrorMessageHandler(final ErrorMessage ex) {
        return ResponseEntity.status(ex.getStatusCode())
                .body(ErrorMessageResponse.builder()
                        .timestamp(new Date())
                        .status(BAD_REQUEST.value())
                        .message(ex.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessageResponse> ErrorMessageHandler(final MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(BAD_REQUEST.value())
                .body(ErrorMessageResponse.builder()
                        .timestamp(new Date())
                        .status(BAD_REQUEST.value())
                        .message(ex.getName())
                        .description(ex.getMessage())
                        .build()
                );
    }


}
