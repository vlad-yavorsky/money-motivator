package com.bepsik.moneymotivator.mapper;

import com.bepsik.moneymotivator.dto.BalanceHistoryDto;
import com.bepsik.moneymotivator.entity.BalanceHistory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface BalanceHistoryMapper {

    BalanceHistoryDto toDto(BalanceHistory entity);
    List<BalanceHistoryDto> toDto(List<BalanceHistory> entity);

}
