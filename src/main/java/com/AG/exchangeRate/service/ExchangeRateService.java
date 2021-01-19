package com.AG.exchangeRate.service;

import com.AG.exchangeRate.model.dto.ExchangeRateResponseDto;
import com.AG.exchangeRate.model.dto.HistoryExchangeRateAPIResponse;
import com.AG.exchangeRate.model.enums.Currency;
import com.AG.exchangeRate.model.enums.RateTrend;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Rose
 */
public interface ExchangeRateService {
    ExchangeRateResponseDto getExchangeRateForGivenDateAndCurrencies(String date, Currency baseCurrency, Currency targetCurrency);


    BigDecimal calculateAverage(HistoryExchangeRateAPIResponse historyExchangeRateAPIResponse, Currency target);

    RateTrend determineRateTrend(TreeMap<LocalDate, Map<Currency, BigDecimal>> rates, Currency target);
}
