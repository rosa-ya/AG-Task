package com.AG.exchangeRate.model.dto;

import com.AG.exchangeRate.model.enums.Currency;
import com.AG.exchangeRate.model.enums.RateTrend;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Rose
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateResponseDto {

    private Double exchangeRate;
    private BigDecimal average;
    private RateTrend trend;
    private Date creationDate;
    private Currency base;
    private Currency target;
    private Date exchangeRateStartDate;

}
