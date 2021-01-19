package com.AG.exchangeRate.service;

import com.AG.exchangeRate.model.enums.Currency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * @author Rose
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class ExchangeRateClientTest {

    @Autowired
    ExchangeRateClient exchangeRateClient;

    @Test
    public void feignGetExchangeRateForDateTest() {
        String date = "2000-10-23";
        Currency base = Currency.GBP;
        Currency target = Currency.CZK;

        assertNotNull(this.exchangeRateClient.getExchangeRateForDate(date, base, target).getBody());

    }

    @Test
    public void feignGetHistoricalExchangeRateTest() {
        String start_at = "2000-10-23";
        String end_at = "2000-11-03";
        Currency base = Currency.GBP;
        Currency target = Currency.CZK;

        assertNotNull(this.exchangeRateClient.getHistoricalExchangeRate(start_at, end_at, base, target).getBody());

    }
}
