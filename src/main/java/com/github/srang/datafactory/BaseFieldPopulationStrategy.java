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

import com.github.javafaker.Faker;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;


/**
 * <p>Abstract BaseFieldPopulationStrategy class.</p>
 *
 * @author srang
 */
public abstract class BaseFieldPopulationStrategy implements FieldPopulationStrategy {
    /** Constant <code>PACKAGE_PREFIX="package com"</code> */
    protected static final String PACKAGE_PREFIX = "package com"; // TODO get from constructor
    protected Faker faker;
    protected List<String> ignoredFields;
    protected LinkedList<Filter> filters;

    /**
     * <p>create.</p>
     *
     * @param c a {@link java.lang.Class} object.
     * @param faker a {@link com.github.javafaker.Faker} object.
     * @param <T> a T object.
     * @return a T object.
     * @throws com.github.srang.datafactory.ObjectFieldGenerationException if any.
     */
    public static <T extends BaseFieldPopulationStrategy> T create(Class<T> c, Faker faker) throws ObjectFieldGenerationException {
        try {
            T populator = c.getConstructor(Faker.class).newInstance(faker);
            populator.init();
            return populator;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ObjectFieldGenerationException("Could not instantiate populator", e);
        }
    }

    /**
     * <p>Constructor for BaseFieldPopulationStrategy.</p>
     *
     * @param faker a {@link com.github.javafaker.Faker} object.
     */
    protected BaseFieldPopulationStrategy(Faker faker) {
        this.ignoredFields = new ArrayList<>();
        this.filters = new LinkedList<>();
        this.faker = faker;
    }

    /**
     * ensures that object and ignore filter get added absolutely last
     */
    public void init() {
        //object
        this.addFilter((Faker faker, Field field, Object obj) -> {
                if (field.getType().equals(List.class)) {
                    ParameterizedType stringListType = (ParameterizedType) field.getGenericType();
                    Class listFieldClass = (Class) stringListType.getActualTypeArguments()[0];
//                    if (listFieldClass.getPackage().toString().startsWith(PACKAGE_PREFIX))) {
                        throw new ObjectFieldGenerationException("Field is a list of generatable objects");
//                    }
                } else if (field.getType().getPackage() != null // check for primitives
                        && field.getType().getPackage().toString().startsWith(PACKAGE_PREFIX)) {
                    throw new ObjectFieldGenerationException("Field is object and needs to be recursively generated");
                }
                return false;
            }
        );

        // ignored fields
        this.addFilter(
                (Faker faker, Field field, Object obj) -> ignoredFields.contains(field.getName().toLowerCase())
        );
    }
    /** {@inheritDoc} */
    @Override
    public abstract boolean populateField(Object obj, Field field) throws ObjectFieldGenerationException, MalformedFilterException;


    /** {@inheritDoc} */
    @Override
    public abstract void ignoreField(String fieldName);

    /**
     * <p>addFilter.</p>
     *
     * @param filter a {@link com.github.srang.datafactory.BaseFieldPopulationStrategy.Filter} object.
     */
    protected void addFilter(Filter filter) {
        this.filters.push(filter);
    }

    /** {@inheritDoc} */
    @Override
    public void addFilter(Predicate<Field> check, Supplier<?> evaluator) {
        addFilter(new FilterProcessor(check, evaluator));
    }

    protected class FilterProcessor implements Filter {
        Predicate<Field> check;
        Supplier evaluate;

        public FilterProcessor (Predicate<Field> check, Supplier<?> evaluate) {
            this.check = check;
            this.evaluate = evaluate;
        }

        @Override
        public boolean process(Faker faker, Field field, Object obj) throws ObjectFieldGenerationException,MalformedFilterException {
            if (check.test(field)) {
                try {
                    field.set(obj, field.getType().cast(evaluate.get()));
                    return true;
                }catch(IllegalAccessException e){
                    e.printStackTrace();
                }
            }
            return false;
        }
    }

    @FunctionalInterface
    protected interface Filter {
        boolean process(Faker faker, Field field, Object obj) throws ObjectFieldGenerationException,MalformedFilterException;
    }
}
