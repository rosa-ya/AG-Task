package com.AG.exchangeRate.model.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.text.MessageFormat;
import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author Rose
 */
@Getter
@AllArgsConstructor
public enum ErrorCreation {

    INVALID_DATE_INPUT(BAD_REQUEST, "Invalid date. {0}"),
    INVALID_INPUT(BAD_REQUEST, "Invalid input. ");


    private HttpStatus status;
    private String message;


    public ErrorMessage ErrorCreation(final Object... params) {
        return ErrorMessage.builder()
                .timestamp(new Date())
                .statusCode(status.value())
                .message(MessageFormat.format(message, params))
                .build();

    }

}
