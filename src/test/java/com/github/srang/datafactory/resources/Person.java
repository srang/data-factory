package com.github.srang.datafactory.resources;

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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Person {
  String name;
  String firstName;
  String lastName;
  String address;
  String organization;
  Integer employeeId;
  Integer favoriteNumber;
  BigDecimal salary;
  Date birthdate;
  Date workAnniversary;
  LastObject nested;
  List<String> childrensNames;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getOrganization() {
    return organization;
  }

  public void setOrganization(String organization) {
    this.organization = organization;
  }

  public Integer getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(Integer employeeId) {
    this.employeeId = employeeId;
  }

  public Integer getFavoriteNumber() {
    return favoriteNumber;
  }

  public void setFavoriteNumber(Integer favoriteNumber) {
    this.favoriteNumber = favoriteNumber;
  }

  public BigDecimal getSalary() {
    return salary;
  }

  public void setSalary(BigDecimal salary) {
    this.salary = salary;
  }

  public Date getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(Date birthdate) {
    this.birthdate = birthdate;
  }

  public Date getWorkAnniversary() {
    return workAnniversary;
  }

  public void setWorkAnniversary(Date workAnniversary) {
    this.workAnniversary = workAnniversary;
  }

  public LastObject getNested() {
    return nested;
  }

  public void setNested(LastObject nested) {
    this.nested = nested;
  }

  public List<String> getChildrensNames() {
    return childrensNames;
  }

  public void setChildrensNames(List<String> childrensNames) {
    this.childrensNames = childrensNames;
  }

  @Override
  public String toString() {
    return "SubObject{" +
        "name='" + name + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", address='" + address + '\'' +
        ", organization='" + organization + '\'' +
        ", employeeId=" + employeeId +
        ", favoriteNumber=" + favoriteNumber +
        ", salary=" + salary +
        ", birthdate=" + birthdate +
        ", workAnniversary=" + workAnniversary +
        ", nested=" + nested +
        ", childrensNames=" + childrensNames +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Person subObject = (Person) o;
    return Objects.equals(name, subObject.name) &&
        Objects.equals(firstName, subObject.firstName) &&
        Objects.equals(lastName, subObject.lastName) &&
        Objects.equals(address, subObject.address) &&
        Objects.equals(organization, subObject.organization) &&
        Objects.equals(employeeId, subObject.employeeId) &&
        Objects.equals(favoriteNumber, subObject.favoriteNumber) &&
        Objects.equals(salary, subObject.salary) &&
        Objects.equals(birthdate, subObject.birthdate) &&
        Objects.equals(workAnniversary, subObject.workAnniversary) &&
        Objects.equals(nested, subObject.nested) &&
        Objects.equals(childrensNames, subObject.childrensNames);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, firstName, lastName, address, organization, employeeId, favoriteNumber, salary, birthdate, workAnniversary, nested, childrensNames);
  }
}
