package com.bepsik.moneymotivator.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequestDto {

    @Email
    @NotBlank
    @Size(min = 5, max = 255)
    private String email;

    @NotBlank
    @Size(max = 255)
    private String password;

}
