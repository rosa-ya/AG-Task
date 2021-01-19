package com.AG.exchangeRate.controller;

import com.AG.exchangeRate.model.dto.ExchangeRateResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Rose
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExchangeRateControllerTest {


    @LocalServerPort
    int port;


    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void getExchangeRateForGivenDateAndCurrenciesTest() {
        ExchangeRateResponseDto exchangeRateResponseDto = this.restTemplate.getForObject
                ("http://localhost:" + port + "/api/exchange-rate/2000-10-23/GBP/USD", ExchangeRateResponseDto.class);

        assertNotNull(exchangeRateResponseDto);
    }
}
