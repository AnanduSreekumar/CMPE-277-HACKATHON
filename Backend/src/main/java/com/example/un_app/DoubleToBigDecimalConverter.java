package com.example.un_app;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import java.math.BigDecimal;

@ReadingConverter
public class DoubleToBigDecimalConverter implements Converter<Double, BigDecimal> {

    @Override
    public BigDecimal convert(Double source) {
        if (source == null || source.isNaN()) {
            return null; // or a default value of your choice
        }
        return BigDecimal.valueOf(source);
    }
}

