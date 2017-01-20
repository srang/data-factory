package com.github.srang.datafactory;

import com.github.javafaker.Faker;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BaseFactory<T> {
    private FieldPopulationStrategy populator;

    protected Class clazz;

    public BaseFactory(Class clazz) {
        this(clazz, null, null, null);
    }

    public BaseFactory(Class clazz, Class claxx) {
        this(clazz, null, null, claxx);
    }

    public BaseFactory(Class clazz, Random seed) {
        this(clazz, null, seed, null);
    }

    public BaseFactory(Class clazz, Locale locale) {
        this(clazz, locale, null, null);
    }

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

    public BaseFactory(Class clazz, FieldPopulationStrategy populator) {
        this.clazz = clazz;
        this.populator = populator;
    }



    public void ignoreField(String fieldName) {
        this.populator.ignoreField(fieldName);
    }

    public void addFilter(Method filter) throws MalformedFilterException {
        this.populator.addFilter(filter);
    }

    public void addFilter(Predicate<Field> a, Supplier<?> b) {
        this.populator.addFilter(a, b);
    }

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
