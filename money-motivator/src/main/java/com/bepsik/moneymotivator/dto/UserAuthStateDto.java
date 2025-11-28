package com.bepsik.moneymotivator.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserAuthStateDto {

    private boolean authenticated;
    private UserDto user;

}
