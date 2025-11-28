package com.bepsik.moneymotivator.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ProjectDto {

    private Long id;
    private String name;
    private String description;
    private String ownerEmail;
    private LocalDateTime created;

}
