package com.bepsik.moneymotivator.dto;

import com.bepsik.moneymotivator.enumeration.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class UserDto {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime created;
    private List<Role> roles;
    private BigDecimal balance;

}
