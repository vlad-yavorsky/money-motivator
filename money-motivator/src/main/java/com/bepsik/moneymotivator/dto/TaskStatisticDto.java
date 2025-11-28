package com.bepsik.moneymotivator.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskStatisticDto {

    private Integer year;
    private Integer month;
    private Long count;

}
