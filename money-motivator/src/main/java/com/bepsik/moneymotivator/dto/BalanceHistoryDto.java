package com.bepsik.moneymotivator.dto;

import com.bepsik.moneymotivator.enumeration.BalanceOperation;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class BalanceHistoryDto {

    private BalanceOperation operation;
    private BigDecimal value;
    private LocalDateTime date;
    private BigDecimal remaining;

}
