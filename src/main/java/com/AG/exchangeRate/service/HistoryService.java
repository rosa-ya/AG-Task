package com.AG.exchangeRate.service;

import com.AG.exchangeRate.model.dto.ExchangeRateResponseDto;
import com.AG.exchangeRate.model.entity.ExchangeRateEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Rose
 */
public interface HistoryService {


    ExchangeRateEntity saveExchangeRate(ExchangeRateResponseDto exchangeRateResultDto);

    List<ExchangeRateResponseDto> getDailyHistory(Integer year, Integer month, Integer day, Pageable pageable);

    List<ExchangeRateResponseDto> getMonthlyHistory(Integer year, Integer month,Pageable pageable);
}
