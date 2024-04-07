package com.bepsik.moneymotivator.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ProjectUserId implements Serializable {

    private Long projectId;
    private Long userId;

}
