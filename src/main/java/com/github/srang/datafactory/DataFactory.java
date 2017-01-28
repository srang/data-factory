package com.github.srang.datafactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface DataFactory<T> {
    void ignoreField(String fieldName);

    void addFilter(Predicate<Field> a, Supplier<?> b);

    T generate();

    List<T> generate(int count);

    void setPopulator(FieldPopulationStrategy populator);
}
