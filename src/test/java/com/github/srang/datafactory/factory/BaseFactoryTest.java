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
import com.github.srang.datafactory.resources.ObjectFieldPopulationStrategyImpl;
import com.github.srang.datafactory.resources.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.oneOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class BaseFactoryTest {
    @Test
    public void testBaseFactoryCreation() {
        DataFactory<Citizen> objectFactory = new BaseFactory<>(Citizen.class);
        assertNotNull("Factory should be instantiatable", objectFactory);
        Citizen object = objectFactory.generate();
        assertNotNull("Object should be generated", object);
        assertNotNull("Object should have Integers in it", object.getEmployeeId());
        assertNotNull("Object should have Longs in it", object.getCitizenId());
        assertNotNull("Object should have Strings in it", object.getOrganization());
        assertNotNull("Object should have inherited fields", object.getSecretId());
    }

    @Test
    public void testBaseFactoryListCreation() {
        DataFactory<Citizen> objectFactory = new BaseFactory<>(Citizen.class);
        assertNotNull(objectFactory);
        List<Citizen> objects = objectFactory.generate(6);
        assertNotNull("Objects should be generated", objects);
        assertEquals("There should be 6 objects in the list", 6, objects.size());
        assertNotNull("The objects in the list should be complete", objects.get(0));
    }

    @Test
    public void testAddFilterCreation() {
        DataFactory<Person> personFactory = new BaseFactory<>(Person.class);
        personFactory.addFilter(
                (Field field) -> field.getType().equals(String.class) && field.getName().toLowerCase().contains("firstname"),
                () -> "Hermione" );
        Person person = personFactory.generate();

        assertNotNull("Person should not be null", person);
        assertEquals("Person should have first name from lambda filter", "Hermione", person.getFirstName());
    }

    @Test
    public void testAddNestedFilterCreation() {
        DataFactory<Citizen> objectFactory = new BaseFactory<>(Citizen.class);
        objectFactory.addFilter(
                (Field field) -> field.getType().equals(String.class) && field.getName().toLowerCase().contains("firstname"),
                () -> "Hermione" );
        Citizen object = objectFactory.generate();

        assertNotNull("Nested person should not be null", object.getOneObject());
        assertEquals("Nested person first name should be from added filter", "Hermione", object.getOneObject().getFirstName());
    }

    @Test
    public void testLoadProperties() {
        Locale l =  new Locale("eng","SRANG");
        assertNotNull("locale should be loaded", l);
        DataFactory<Person> personFactory = new BaseFactory<>(Person.class, l);
        assertNotNull("Factory should be instantiatable with property files specified", personFactory);
        Person person = personFactory.generate();
        assertThat("Person first name should come from property file", person.getFirstName(), is(oneOf("srang", "srangatang")));
    }

    @Test
    public void testLoadBuiltInLang() {
        DataFactory<Person> personFactory = new BaseFactory<>(Person.class, new Locale("es"));
        assertNotNull("Factory should be instantiatable with built in language", personFactory);
        Person person = personFactory.generate();
        assertNotNull("Person should still get a first name", person.getFirstName());
    }

    @Test
    public void testSeedRNG() {
        DataFactory<Citizen> objectFactoryA = new BaseFactory<>(Citizen.class, new Random(123));
        DataFactory<Citizen> objectFactoryB = new BaseFactory<>(Citizen.class, new Random(123));

        assertNotNull("Factory should be instantiatable with random seed", objectFactoryA);
        Citizen objectA = objectFactoryA.generate();
        Citizen objectB = objectFactoryB.generate();
        Citizen objectC = objectFactoryA.generate();
        assertNotNull("Object should generate nested fields", objectA.getOneObject());
        assertEquals("Objects should have identical identifiers", objectA.getEmployeeId(), objectB.getEmployeeId());
        assertEquals("Objects should have identical fields", objectA, objectB);
        assertEquals("Nested objects should follow seeding", objectA.getOneObject().getFirstName(), objectB.getOneObject().getFirstName());
        assertNotEquals("Subsequent objects with same seed should be different", objectA.getOneObject(), objectC.getOneObject());
    }

    @Test
    public void testNestedObjectList() {
        DataFactory<Citizen> objectFactory = new BaseFactory<>(Citizen.class);
        Citizen object = objectFactory.generate();
        assertNotNull("Factory should be able to generate lists of generatable objects", object.getObjects().get(0).getChildrensNames());
    }

    @Test
    public void testIgnoreFilter() {
        DataFactory<Citizen> objectFactory = new BaseFactory<>(Citizen.class);
        objectFactory.ignoreField("firstName");
        Citizen object = objectFactory.generate();
        assertNull("Factory should be able to ignore inherited fields", object.getFirstName());
        assertNotNull("Factory should be able to generate inherited fields", object.getSecretId());
        assertNull("Factory should ignore nested fields", object.getOneObject().getFirstName());
        assertNotNull("Factory should still populate non ignored fields", object.getOneObject().getLastName());

        objectFactory.ignoreField("objects");
        object = objectFactory.generate();
        assertNull("Factory should be able to ignore objects", object.getObjects());
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
        assertNotNull("Object should not be null", spanishObject);
        assertNotNull("Object should not be null", englishObject);
        assertNotNull("Object should not be null", englishObjectB);

        assertEquals("English objects should be same", englishObject.getOneObject(), englishObjectB.getOneObject());
        assertNotEquals("Spanish and English objects should be different", englishObject.getOneObject(), spanishObject.getOneObject());
    }

    @Test
    public void testSpecifyPopulator() throws Exception {
        DataFactory<Citizen> objectFactory = new BaseFactory<>(Citizen.class,
                BaseFieldPopulationStrategy.create(ObjectFieldPopulationStrategyImpl.class, new Faker()));

        assertNotNull("Factory should be created", objectFactory);
        Citizen object = objectFactory.generate();
        assertThat("Factory should generate stuff", object.getSecretId(), matchesPattern("[a-z]+"));


        objectFactory.addFilter(
                (Field field) -> field.getName().equals("secretId"),
                () -> "ASDF"
        );

        object = objectFactory.generate();
        assertEquals("Factory should generate stuff", "ASDF", object.getSecretId());

    }

    @Test
    public void testAddressPopulation() {
        DataFactory<Citizen> objectFactory = new BaseFactory<>(Citizen.class);
        Citizen object = objectFactory.generate();
        assertThat("Address should look like an address", object.getAddress(),
                matchesPattern("[a-zA-Z. ]*([0-9]+ ){1,2}[A-Za-z. ']+, [a-zA-Z -']+, [A-Z]{2} [0-9]{5}(-[0-9]{4})?"));
    }
}
