package com.github.srang.datafactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface FieldPopulationStrategy {


    /**
     * Try to set the field on obj of class clazz. Return whether or not
     * assignment was successful
     *
     * @param obj
     * @param field
     * @return
     */
    public boolean populateField(Object obj, Field field) throws ObjectFieldGenerationException, MalformedFilterException;


    /**
     * Add filter to the filter chain
     * @param filter
     */
    public void addFilter(Method filter) throws MalformedFilterException;

    /**
     * Use lambdas to add filters to the chain
     * @param check
     * @param evaluator
     */
    public void addFilter(Predicate<Field> check, Supplier<?> evaluator);

    /**
     * Ignore field when populating object
     * @param fieldName
     */
    public void ignoreField(String fieldName);
}
