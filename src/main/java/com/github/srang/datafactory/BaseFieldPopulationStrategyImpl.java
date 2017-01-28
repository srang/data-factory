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
import com.github.srang.datafactory.util.DateUtil;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

/**
 * <p>BaseFieldPopulationStrategyImpl class.</p>
 *
 * @author srang
 */
public class BaseFieldPopulationStrategyImpl extends BaseFieldPopulationStrategy {

    /**
     * <p>Constructor for BaseFieldPopulationStrategyImpl.</p>
     *
     * @param faker a {@link com.github.javafaker.Faker} object.
     */
    public BaseFieldPopulationStrategyImpl(Faker faker) {
        super(faker);
        ignoreField("serialversionuid");
        addBaseFilters();
    }

    /** {@inheritDoc} */
    @Override
    public boolean populateField(Object obj, Field field) throws ObjectFieldGenerationException,MalformedFilterException {
        return this.doFilter(obj, field);
    }

    /** {@inheritDoc} */
    @Override
    public void ignoreField(String ignoredField) {
        this.ignoredFields.add(ignoredField.toLowerCase());
    }


    private void addBaseFilters() {
        //base string
        this.addFilter(
                (Field field) -> field.getType().equals(String.class),
                () -> faker.lorem().word()
        );

        // base integer
        this.addFilter(
                (Field field) -> field.getType().equals(Integer.class),
                () -> faker.number().numberBetween(0, Integer.MAX_VALUE)
        );

        // base big decimal
        this.addFilter(
                (Field field) -> field.getType().equals(BigDecimal.class),
                () -> new BigDecimal(faker.number().randomDouble(2, 1, 5000))
        );

        // base big decimal
        this.addFilter(
            (Field field) -> field.getType().equals(Long.class),
            () -> new Long(faker.number().numberBetween(Integer.MAX_VALUE, Long.MAX_VALUE))
        );

        // baseDate
        this.addFilter(
                (Field field) -> field.getType().equals(Date.class),
                () -> {
                    Date date;
                    try {
                        date = faker.date().between(DateUtil.getDateOnlyFromString("1901-01-01"), DateUtil.getDateOnlyFromString("2016-01-01"));
                    } catch (ParseException e) {
                        date = new Date();
                    }
                    return date;
                }
        );

        // birthDate
        this.addFilter(
                (Field field) -> field.getType().equals(Date.class) && field.getName().toLowerCase().contains("birthdate"),
                () -> {
                    Date date;
                    try {
                        date = faker.date().between(DateUtil.getDateOnlyFromString("1901-01-01"), DateUtil.getDateOnlyFromString("2006-01-01"));
                    } catch (ParseException e) {
                        date = new Date();
                    }
                    return date;
                }
        );

        // lastName
        this.addFilter(
                (Field field) -> field.getType().equals(String.class) && field.getName().toLowerCase().contains("lastname"),
                () -> faker.name().lastName()
        );

        // firstName
        this.addFilter(
                (Field field) -> field.getType().equals(String.class) && field.getName().toLowerCase().contains("firstname"),
                () -> faker.name().firstName()
        );




        String[] filters = {
                "organization", //
                "address" //
        };

    }

    /**
     * <p>doFilter.</p>
     *
     * @param obj a {@link java.lang.Object} object.
     * @param field a {@link java.lang.reflect.Field} object.
     * @return a boolean.
     * @throws com.github.srang.datafactory.ObjectFieldGenerationException if any.
     * @throws com.github.srang.datafactory.MalformedFilterException if any.
     */
    protected boolean doFilter(Object obj, Field field) throws ObjectFieldGenerationException,MalformedFilterException {
        for (Filter filter : filters) {
            if (filter.process(faker, field, obj)) {
                return true;
            }
        }
        return false;
    }

}
