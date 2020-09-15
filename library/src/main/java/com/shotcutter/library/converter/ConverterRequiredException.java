package com.shotcutter.library.converter;

public class ConverterRequiredException extends Exception {

    public ConverterRequiredException(Class original, Class target) {
        super(String.format("Converters for next classes required: %s, %s", original.getName(), target.getName()));
    }

}
