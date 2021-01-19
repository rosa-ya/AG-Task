package com.AG.exchangeRate.model.errors;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Rose
 */
@Getter
@Setter
@Builder
public class ErrorMessageResponse {

    private int status;
    private Date timestamp;
    private String message;
    private String description;
}
