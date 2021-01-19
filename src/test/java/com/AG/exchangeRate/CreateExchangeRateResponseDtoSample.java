package com.AG.exchangeRate;

import com.AG.exchangeRate.model.dto.ExchangeRateResponseDto;
import com.AG.exchangeRate.model.enums.Currency;
import com.AG.exchangeRate.model.enums.RateTrend;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Rose
 */
public class CreateExchangeRateResponseDtoSample {

    public static ExchangeRateResponseDto createResponseSample1() throws ParseException {
        return ExchangeRateResponseDto.builder()
                .average(BigDecimal.valueOf(1.45907))
                .base(Currency.GBP)
                .exchangeRate(1.438050101)
                .creationDate(new Date())
                .target(Currency.USD)
                .exchangeRateStartDate(new SimpleDateFormat("yyyy-MM-dd").parse("2000-10-11"))
                .trend(RateTrend.UNDEFINED)
                .build();
    }

    public static ExchangeRateResponseDto createResponseSample2() throws ParseException {
        return ExchangeRateResponseDto.builder()
                .average(BigDecimal.valueOf(35.39133))
                .base(Currency.EUR)
                .exchangeRate(35.357)
                .creationDate(new Date())
                .target(Currency.CZK)
                .exchangeRateStartDate(new SimpleDateFormat("yyyy-MM-dd").parse("2000-10-11"))
                .trend(RateTrend.UNDEFINED)
                .build();
    }

    public static ExchangeRateResponseDto createResponseSample3() throws ParseException {
        return ExchangeRateResponseDto.builder()
                .average(BigDecimal.valueOf(0.52918))
                .base(Currency.AUD)
                .exchangeRate(0.5194106499)
                .creationDate(new Date())
                .target(Currency.USD)
                .exchangeRateStartDate(new SimpleDateFormat("yyyy-MM-dd").parse("2000-10-11"))
                .trend(RateTrend.UNDEFINED)
                .build();
    }

}
