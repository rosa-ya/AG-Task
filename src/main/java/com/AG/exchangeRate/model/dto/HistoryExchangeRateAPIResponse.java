package com.AG.exchangeRate.model.dto;

import com.AG.exchangeRate.model.enums.Currency;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Rose
 */
@Data
public class HistoryExchangeRateAPIResponse {

    private TreeMap<LocalDate, Map<Currency, BigDecimal>> rates;
    private LocalDate start_at;
    private LocalDate end_at;
    private Currency base;
}
