package com.AG.exchangeRate.controller;

import com.AG.exchangeRate.CreateExchangeRateEntitySample;
import com.AG.exchangeRate.CreateExchangeRateResponseDtoSample;
import com.AG.exchangeRate.model.dto.ExchangeRateResponseDto;
import com.AG.exchangeRate.model.entity.ExchangeRateEntity;
import com.AG.exchangeRate.service.HistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author Rose
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(HistoryController.class)
public class HistoryControllerTest {


    @MockBean
    private HistoryService historyService;


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getDailyHistoryTest() throws Exception {


        when(historyService.saveExchangeRate(any(ExchangeRateResponseDto.class))).thenReturn(CreateExchangeRateEntitySample.createEntitySample1());

        String dateString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Integer year = Integer.valueOf(dateString.substring(0, 4));
        Integer month = Integer.valueOf(dateString.substring(5, 7));
        Integer day = Integer.valueOf(dateString.substring(8, 10));
        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        when(historyService.getDailyHistory(year, month, day, pageable)).thenReturn(Arrays.asList(CreateExchangeRateResponseDtoSample.createResponseSample1()));

        mockMvc.perform(
                get("/api/exchange-rate/history/daily/" + year + "/" + month + "/" + day)
                        .param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    public void getMonthlyHistoryTest() throws Exception {
        ExchangeRateEntity exchangeRateEntity1 = CreateExchangeRateEntitySample.createEntitySample1();
        ExchangeRateEntity exchangeRateEntity2 = CreateExchangeRateEntitySample.createEntitySample2();


        when(historyService.saveExchangeRate(any(ExchangeRateResponseDto.class))).thenReturn(exchangeRateEntity1);
        when(historyService.saveExchangeRate(any(ExchangeRateResponseDto.class))).thenReturn(exchangeRateEntity2);

        String dateString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Integer year = Integer.valueOf(dateString.substring(0, 4));
        Integer month = Integer.valueOf(dateString.substring(5, 7));
        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        ExchangeRateResponseDto exchangeRateResponseDto1 = CreateExchangeRateResponseDtoSample.createResponseSample1();
        ExchangeRateResponseDto exchangeRateResponseDto2 = CreateExchangeRateResponseDtoSample.createResponseSample2();

        when(historyService.getMonthlyHistory(year, month, pageable)).thenReturn(Arrays.asList(exchangeRateResponseDto1, exchangeRateResponseDto2));

        mockMvc.perform(
                get("/api/exchange-rate/history/monthly/" + year + "/" + month)
                        .param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andDo(print());

    }


}
