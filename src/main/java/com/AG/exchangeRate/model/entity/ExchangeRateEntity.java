package com.AG.exchangeRate.model.entity;

import com.AG.exchangeRate.model.enums.Currency;
import com.AG.exchangeRate.model.enums.RateTrend;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Rose
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    private Currency base;
    private Currency target;
    private Double exchangeRate;
    private BigDecimal average;
    private RateTrend trend;
    @Temporal(TemporalType.DATE)
    private Date exchangeRateStartDate;
}
