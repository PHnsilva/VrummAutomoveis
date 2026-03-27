package com.vrumm.converter;

import com.vrumm.domain.Cpf;
import io.micronaut.core.convert.ConversionContext;
import io.micronaut.core.convert.TypeConverter;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class StringToCpfConverter implements TypeConverter<String, Cpf> {

    @Override
    public Optional<Cpf> convert(String object, Class<Cpf> targetType, ConversionContext context) {
        if (object == null || object.isBlank()) {
            return Optional.empty();
        }

        try {
            return Optional.of(new Cpf(object));
        } catch (IllegalArgumentException e) {
            context.reject(object, e);
            return Optional.empty();
        }
    }
}