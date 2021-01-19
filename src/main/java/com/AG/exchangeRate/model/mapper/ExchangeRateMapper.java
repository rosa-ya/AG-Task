package com.AG.exchangeRate.model.mapper;

import com.AG.exchangeRate.model.dto.ExchangeRateResponseDto;
import com.AG.exchangeRate.model.entity.ExchangeRateEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Rose
 */
@Component
public class ExchangeRateMapper {

    public ExchangeRateEntity mapDtoToEntity(ExchangeRateResponseDto exchangeRateResponseDto) {
        return ExchangeRateEntity.builder()
                .average(exchangeRateResponseDto.getAverage())
                .base(exchangeRateResponseDto.getBase())
                .exchangeRate(exchangeRateResponseDto.getExchangeRate())
                .target(exchangeRateResponseDto.getTarget())
                .trend(exchangeRateResponseDto.getTrend())
                .creationDate(new Date())
                .exchangeRateStartDate(exchangeRateResponseDto.getExchangeRateStartDate())
                .build();
    }

    public ExchangeRateResponseDto mapEntityToDto(ExchangeRateEntity exchangeRateEntity) {
        return ExchangeRateResponseDto.builder()
                .average(exchangeRateEntity.getAverage())
                .base(exchangeRateEntity.getBase())
                .exchangeRate(exchangeRateEntity.getExchangeRate())
                .target(exchangeRateEntity.getTarget())
                .trend(exchangeRateEntity.getTrend())
                .creationDate(exchangeRateEntity.getCreationDate())
                .exchangeRateStartDate(exchangeRateEntity.getExchangeRateStartDate())
                .build();
    }

    public List<ExchangeRateResponseDto> mapEntityToDtoList (List<ExchangeRateEntity> exchangeRateEntityList) {
        return exchangeRateEntityList.stream().map(x -> this.mapEntityToDto(x)).collect(Collectors.toList());
    }
}
