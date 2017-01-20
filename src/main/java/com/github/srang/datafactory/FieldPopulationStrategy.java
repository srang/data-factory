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
