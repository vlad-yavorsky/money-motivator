package com.bepsik.moneymotivator.mapper;

import com.bepsik.moneymotivator.dto.UserDto;
import com.bepsik.moneymotivator.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto user);

}
