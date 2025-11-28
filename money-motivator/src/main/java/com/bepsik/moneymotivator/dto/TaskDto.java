package com.bepsik.moneymotivator.dto;

import com.bepsik.moneymotivator.enumeration.TaskStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class TaskDto {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private TaskStatus status;
    private String authorEmail;
    private String assigneeEmail;
    private Long projectId;

}
