package com.bepsik.moneymotivator.service;

import com.bepsik.moneymotivator.enumeration.Role;
import com.bepsik.moneymotivator.exception.InternalServerErrorException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.List;

@Converter
public class UserRolesConverter implements AttributeConverter<List<Role>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Role> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new InternalServerErrorException("Error converting list to JSON", e);
        }
    }

    @Override
    public List<Role> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<Role>>() {});
        } catch (IOException e) {
            throw new InternalServerErrorException("Error converting JSON to list", e);
        }
    }
}
