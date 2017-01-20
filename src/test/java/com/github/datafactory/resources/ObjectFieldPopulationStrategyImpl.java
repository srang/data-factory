package com.github.datafactory.resources;

import com.github.datafactory.BaseFieldPopulationStrategyImpl;
import com.github.javafaker.Faker;

import java.lang.reflect.Field;

public class ObjectFieldPopulationStrategyImpl extends BaseFieldPopulationStrategyImpl {
  public ObjectFieldPopulationStrategyImpl(Faker faker) {
    super(faker);
    addFilters();
  }
  private void addFilters() {
    this.addFilter(
        (Field field) -> field.getType().equals(String.class) && field.getName().equals("secretId"),
        () -> faker.bothify("??")
    );
  }
}