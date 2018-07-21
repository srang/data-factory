package com.github.srang.datafactory;

import com.github.javafaker.Faker;

import java.lang.reflect.Field;

@FunctionalInterface
public interface Filter {
    boolean process(Faker faker, Field field, Object obj) throws ObjectFieldGenerationException,MalformedFilterException;
}
