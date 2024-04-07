package com.bepsik.moneymotivator.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProjectDto {

    private Long id;
    private String name;
    private String description;
    private String ownerEmail;
    private LocalDateTime created;

}
