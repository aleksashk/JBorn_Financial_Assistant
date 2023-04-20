package com.philimonov.converter;

public interface Converter<S, T> {
    T convert(S source);
}
