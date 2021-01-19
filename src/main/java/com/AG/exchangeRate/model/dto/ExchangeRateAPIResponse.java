package com.AG.exchangeRate.model.dto;

import com.AG.exchangeRate.model.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

/**
 * @author Rose
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateAPIResponse {

    private Currency base;
    private Date date;
    private Map<Currency, Double> rates;


}

