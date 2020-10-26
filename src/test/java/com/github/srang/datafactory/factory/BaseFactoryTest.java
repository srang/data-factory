package com.github.srang.datafactory.factory;

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

import com.github.srang.datafactory.BaseFactory;
import com.github.srang.datafactory.BaseFieldPopulationStrategy;
import com.github.srang.datafactory.DataFactory;
import com.github.srang.datafactory.resources.Citizen;
import com.github.javafaker.Faker;
import com.github.srang.datafactory.resources.Individual;
import com.github.srang.datafactory.resources.ObjectFieldPopulationStrategyImpl;
import com.github.srang.datafactory.resources.Person;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.oneOf;
import static org.junit.jupiter.api.Assertions.*;

public class BaseFactoryTest {
    @Test
    public void testBaseFactoryCreation() {
        DataFactory<Citizen> objectFactory = new BaseFactory<>(Citizen.class);
        assertNotNull(objectFactory, "Factory should be instantiatable");
        Citizen object = objectFactory.generate();
        assertNotNull(object, "Object should be generated");
        assertNotNull(object.getEmployeeId(), "Object should have Integers in it");
        assertNotNull(object.getCitizenId(), "Object should have Longs in it");
        assertNotNull(object.getOrganization(), "Object should have Strings in it");
        assertNotNull(object.getSecretId(), "Object should have inherited fields");
    }

    @Test
    public void testBaseFactoryListCreation() {
        DataFactory<Citizen> objectFactory = new BaseFactory<>(Citizen.class);
        assertNotNull(objectFactory);
        List<Citizen> objects = objectFactory.generate(6);
        assertNotNull(objects, "Objects should be generated");
        assertEquals(6, objects.size(), "There should be 6 objects in the list");
        assertNotNull(objects.get(0), "The objects in the list should be complete");
    }

    @Test
    public void testAddFilterCreation() {
        DataFactory<Person> personFactory = new BaseFactory<>(Person.class);
        personFactory.addFilter(
                (Field field) -> field.getType().equals(String.class) && field.getName().toLowerCase().contains("firstname"),
                () -> "Hermione" );
        Person person = personFactory.generate();

        assertNotNull(person, "Person should not be null");
        assertEquals("Hermione", person.getFirstName(), "Person should have first name from lambda filter");
    }

    @Test
    public void testAddNestedFilterCreation() {
        DataFactory<Citizen> objectFactory = new BaseFactory<>(Citizen.class);
        objectFactory.addFilter(
                (Field field) -> field.getType().equals(String.class) && field.getName().toLowerCase().contains("firstname"),
                () -> "Hermione" );
        Citizen object = objectFactory.generate();

        assertNotNull(object.getOneObject(), "Nested person should not be null");
        assertEquals("Hermione", object.getOneObject().getFirstName(), "Nested person first name should be from added filter");
    }

    @Test
    public void testLoadProperties() {
        Locale l =  new Locale("eng","SRANG");
        assertNotNull(l, "locale should be loaded");
        DataFactory<Person> personFactory = new BaseFactory<>(Person.class, l);
        assertNotNull(personFactory, "Factory should be instantiatable with property files specified");
        Person person = personFactory.generate();
        assertThat("Person first name should come from property file", person.getFirstName(), is(oneOf("srang", "srangatang")));
    }

    @Test
    public void testLoadBuiltInLang() {
        DataFactory<Person> personFactory = new BaseFactory<>(Person.class, new Locale("es"));
        assertNotNull(personFactory, "Factory should be instantiatable with built in language");
        Person person = personFactory.generate();
        assertNotNull(person.getFirstName(), "Person should still get a first name");
    }

    @Test
    public void testSeedRNG() {
        DataFactory<Citizen> objectFactoryA = new BaseFactory<>(Citizen.class, new Random(123));
        DataFactory<Citizen> objectFactoryB = new BaseFactory<>(Citizen.class, new Random(123));

        assertNotNull(objectFactoryA, "Factory should be instantiatable with random seed");
        Citizen objectA = objectFactoryA.generate();
        Citizen objectB = objectFactoryB.generate();
        Citizen objectC = objectFactoryA.generate();
        assertNotNull(objectA.getOneObject(), "Object should generate nested fields");
        assertEquals(objectA.getEmployeeId(), objectB.getEmployeeId(), "Objects should have identical identifiers");
        assertEquals(objectA, objectB, "Objects should have identical fields");
        assertEquals(objectA.getOneObject().getFirstName(), objectB.getOneObject().getFirstName(), "Nested objects should follow seeding");
        assertNotEquals(objectA.getOneObject(), objectC.getOneObject(), "Subsequent objects with same seed should be different");
    }

    @Test
    public void testNestedObjectList() {
        DataFactory<Citizen> objectFactory = new BaseFactory<>(Citizen.class);
        Citizen object = objectFactory.generate();
        assertNotNull(object.getObjects().get(0).getChildrensNames(), "Factory should be able to generate lists of generatable objects");
    }

    @Test
    public void testIgnoreFilter() {
        DataFactory<Citizen> objectFactory = new BaseFactory<>(Citizen.class);
        objectFactory.ignoreField("firstName");
        Citizen object = objectFactory.generate();
        assertNull(object.getFirstName(), "Factory should be able to ignore inherited fields");
        assertNotNull(object.getSecretId(), "Factory should be able to generate inherited fields");
        assertNull(object.getOneObject().getFirstName(), "Factory should ignore nested fields");
        assertNotNull(object.getOneObject().getLastName(), "Factory should still populate non ignored fields");

        objectFactory.ignoreField("objects");
        object = objectFactory.generate();
        assertNull(object.getObjects(), "Factory should be able to ignore objects");
    }

    @Test
    public void testMultiLang() {
        //depends on seeding working
        DataFactory<Citizen> englishFactory = new BaseFactory<>(Citizen.class, new Random(123));
        DataFactory<Citizen> spanishFactory = new BaseFactory<>(Citizen.class, new Locale("es"), new Random(123), null);
        DataFactory<Citizen> englishFactoryB = new BaseFactory<>(Citizen.class, new Locale("en"), new Random(123), null);
        Citizen spanishObject = spanishFactory.generate();
        Citizen englishObjectB = englishFactoryB.generate();
        Citizen englishObject = englishFactory.generate();
        assertNotNull(spanishObject, "Object should not be null");
        assertNotNull(englishObject, "Object should not be null");
        assertNotNull(englishObjectB, "Object should not be null");

        assertEquals(englishObject.getOneObject(), englishObjectB.getOneObject(), "English objects should be same");
        assertNotEquals(englishObject.getOneObject(), spanishObject.getOneObject(), "Spanish and English objects should be different");
    }

    @Test
    public void testSpecifyPopulator() throws Exception {
        DataFactory<Citizen> objectFactory = new BaseFactory<>(Citizen.class,
                BaseFieldPopulationStrategy.create(ObjectFieldPopulationStrategyImpl.class, new Faker()));

        assertNotNull(objectFactory, "Factory should be created");
        Citizen object = objectFactory.generate();
        assertThat("Factory should generate stuff", object.getSecretId(), matchesPattern("[a-z]+"));


        objectFactory.addFilter(
                (Field field) -> field.getName().equals("secretId"),
                () -> "ASDF"
        );

        object = objectFactory.generate();
        assertEquals("ASDF", object.getSecretId(), "Factory should generate stuff");

    }

    @RepeatedTest(10)
    public void testNamePopulation() {
        DataFactory<Individual> objectFactory = new BaseFactory<>(Individual.class);
        Individual object = objectFactory.generate();
        assertThat("Name should look like an name", object.getFullName(),
                matchesPattern("([A-Z][a-z.]+ )?([A-Z][a-zA-Z']+ ?){2,3}( ([IVX]+|MD|DVM|PhD|Jr.|Sr.))?"));
    }

    @RepeatedTest(10)
    public void testAddressPopulation() {
        DataFactory<Citizen> objectFactory = new BaseFactory<>(Citizen.class);
        Citizen object = objectFactory.generate();
        assertThat("Address should look like an address", object.getAddress(),
                matchesPattern("[a-zA-Z. ]*([0-9]+ ){1,2}[A-Za-z. ']+, [a-zA-Z -']+, [A-Z]{2} [0-9]{5}(-[0-9]{4})?"));
    }
}
