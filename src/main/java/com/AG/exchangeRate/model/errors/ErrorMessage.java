package com.AG.exchangeRate.model.errors;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Rose
 */
@Builder
@Getter
@Setter
public class ErrorMessage extends RuntimeException {

    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;


}
