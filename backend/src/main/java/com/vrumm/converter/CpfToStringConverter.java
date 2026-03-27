package com.vrumm.converter;

import com.vrumm.domain.Cpf;
import io.micronaut.core.convert.ConversionContext;
import io.micronaut.core.convert.TypeConverter;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class CpfToStringConverter implements TypeConverter<Cpf, String> {

    @Override
    public Optional<String> convert(Cpf object, Class<String> targetType, ConversionContext context) {
        if (object == null) {
            return Optional.empty();
        }

        return Optional.of(object.getValor());
    }
}