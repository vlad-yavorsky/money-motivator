package com.bepsik.moneymotivator.dto;

import com.bepsik.moneymotivator.enumeration.BalanceOperation;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class BalanceHistoryDto {

    private BalanceOperation operation;
    private BigDecimal value;
    private LocalDateTime date;
    private BigDecimal remaining;

}
