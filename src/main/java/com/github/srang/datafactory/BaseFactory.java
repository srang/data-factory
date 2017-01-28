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
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * <p>BaseFactory class.</p>
 *
 * @author srang
 */
public class BaseFactory<T> implements DataFactory<T> {
    private FieldPopulationStrategy populator;

    protected Class clazz;


    /**
     * <p>Constructor for BaseFactory.</p>
     *
     * @param clazz a {@link java.lang.Class} object.
     */
    public BaseFactory(Class clazz) {
        this(clazz, null, null, null);
    }

    /**
     * <p>Constructor for BaseFactory.</p>
     *
     * @param clazz a {@link java.lang.Class} object.
     * @param claxx a {@link java.lang.Class} object.
     */
    public BaseFactory(Class clazz, Class<? extends BaseFieldPopulationStrategy>  claxx) {
        this(clazz, null, null, claxx);
    }

    /**
     * <p>Constructor for BaseFactory.</p>
     *
     * @param clazz a {@link java.lang.Class} object.
     * @param seed a {@link java.util.Random} object.
     */
    public BaseFactory(Class clazz, Random seed) {
        this(clazz, null, seed, null);
    }

    /**
     * <p>Constructor for BaseFactory.</p>
     *
     * @param clazz a {@link java.lang.Class} object.
     * @param locale a {@link java.util.Locale} object.
     */
    public BaseFactory(Class clazz, Locale locale) {
        this(clazz, locale, null, null);
    }

    /**
     * <p>Constructor for BaseFactory.</p>
     *
     * @param clazz a {@link java.lang.Class} object.
     * @param locale a {@link java.util.Locale} object.
     * @param seed a {@link java.util.Random} object.
     * @param populatorImpl a {@link java.lang.Class} object.
     */
    public BaseFactory(Class clazz, Locale locale, Random seed, Class<? extends BaseFieldPopulationStrategy> populatorImpl) {
        this.clazz = clazz;
        Locale _locale = (locale == null) ? new Locale("en") : locale;
        Random _seed = (seed == null) ? new Random() : seed;
        Faker _faker = new Faker(_locale, _seed);

        try {
            if (populatorImpl != null) {
                this.populator = BaseFieldPopulationStrategy.create(populatorImpl, _faker);
            } else {
                this.populator = BaseFieldPopulationStrategy.create(BaseFieldPopulationStrategyImpl.class, _faker);
            }
        } catch (ObjectFieldGenerationException e) {
            e.printStackTrace();
            throw new NullPointerException("Could not create a field population strategy");
        }

    }

    /**
     * <p>Constructor for BaseFactory.</p>
     *
     * @param clazz a {@link java.lang.Class} object.
     * @param populator a {@link com.github.srang.datafactory.FieldPopulationStrategy} object.
     */
    public BaseFactory(Class clazz, FieldPopulationStrategy populator) {
        this.clazz = clazz;
        this.populator = populator;
    }

    @Override
    public void setPopulator(FieldPopulationStrategy populator) {
        this.populator = populator;
    }


    /**
     * <p>ignoreField.</p>
     *
     * @param fieldName a {@link java.lang.String} object.
     */
    @Override
    public void ignoreField(String fieldName) {
        this.populator.ignoreField(fieldName);
    }

    /**
     * <p>addFilter.</p>
     *
     * @param a a {@link java.util.function.Predicate} object.
     * @param b a {@link java.util.function.Supplier} object.
     */
    @Override
    public void addFilter(Predicate<Field> a, Supplier<?> b) {
        this.populator.addFilter(a, b);
    }

    /**
     * <p>generate.</p>
     *
     * @return a T object.
     */
    @Override
    public T generate() {
        T obj = this.buildOne();
        ReflectionUtils.doWithFields(obj.getClass(), (Field field) -> {
            field.setAccessible(true);
            // try to populate fields with appropriate data
            try {
                populator.populateField(obj, field);
            } catch (ObjectFieldGenerationException e) {
                // field is sub object that we created
                Class factoryClass;
                if (field.getType().equals(List.class)) {
                    ParameterizedType stringListType = (ParameterizedType) field.getGenericType();
                    factoryClass = (Class) stringListType.getActualTypeArguments()[0];
                } else {
                    factoryClass = field.getType();
                }
                BaseFactory subFactory = new BaseFactory(factoryClass, populator);
                try {
                    if (field.getType().equals(List.class)) {
                        field.set(obj, subFactory.generate(3));
                    } else {
                        field.set(obj, subFactory.generate());
                    }
                } catch (IllegalAccessException x) {
                    x.printStackTrace();
                }
            } catch (MalformedFilterException e) {
                throw new RuntimeException(e);
            }
        });
        return obj;
    }

    /**
     * <p>generate.</p>
     *
     * @param count a int.
     * @return a {@link java.util.List} object.
     */
    @Override
    public List<T> generate(int count) {
        List<T> tees = new ArrayList<T>();
        for (int i = 0; i < count; i++) {
            tees.add(this.generate());
        }
        return tees;
    }

    private T buildOne() {
        try {
            return (T) this.clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(this.clazz + " Factory threw exception in BaseFactory generation", e);
        }
    }

}
