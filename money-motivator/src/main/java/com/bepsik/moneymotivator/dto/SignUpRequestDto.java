package com.bepsik.moneymotivator.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SignUpRequestDto {

    @Email
    @NotBlank
    @Size(min = 5, max = 255)
    private String email;

    @NotBlank
    @Size(max = 255)
    @ToString.Exclude
    private String password;

    @NotBlank
    @Size(max = 255)
    private String firstName;

    @NotBlank
    @Size(max = 255)
    private String lastName;

}
