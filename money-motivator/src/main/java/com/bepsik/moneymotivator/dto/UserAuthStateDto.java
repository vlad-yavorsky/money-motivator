package com.bepsik.moneymotivator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthStateDto {

    private boolean authenticated;
    private UserDto user;

}
