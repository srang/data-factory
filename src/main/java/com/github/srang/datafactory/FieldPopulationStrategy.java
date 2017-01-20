package com.github.srang.datafactory;

/*-
 * #%L
 * Data Factory
 * %%
 * Copyright (C) 2017 srang
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * <p>FieldPopulationStrategy interface.</p>
 *
 * @author srang
 */
public interface FieldPopulationStrategy {


    /**
     * Try to set the field on obj of class clazz. Return whether or not
     * assignment was successful
     *
     * @param obj a {@link java.lang.Object} object.
     * @param field a {@link java.lang.reflect.Field} object.
     * @return a boolean.
     * @throws com.github.srang.datafactory.ObjectFieldGenerationException if any.
     * @throws com.github.srang.datafactory.MalformedFilterException if any.
     */
    public boolean populateField(Object obj, Field field) throws ObjectFieldGenerationException, MalformedFilterException;


    /**
     * Add filter to the filter chain
     *
     * @param filter a {@link java.lang.reflect.Method} object.
     * @throws com.github.srang.datafactory.MalformedFilterException if any.
     */
    public void addFilter(Method filter) throws MalformedFilterException;

    /**
     * Use lambdas to add filters to the chain
     *
     * @param check a {@link java.util.function.Predicate} object.
     * @param evaluator a {@link java.util.function.Supplier} object.
     */
    public void addFilter(Predicate<Field> check, Supplier<?> evaluator);

    /**
     * Ignore field when populating object
     *
     * @param fieldName a {@link java.lang.String} object.
     */
    public void ignoreField(String fieldName);
}
