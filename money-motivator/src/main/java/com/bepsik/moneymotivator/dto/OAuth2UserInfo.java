package com.bepsik.moneymotivator.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OAuth2UserInfo {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
}