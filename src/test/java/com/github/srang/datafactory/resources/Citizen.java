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

import java.util.List;
import java.util.Objects;

public class Citizen extends Individual {
  List<Person> objects;
  Person oneObject;
  String lastName;
  String organization;
  Integer employeeId;
  Long citizenId;
  String address;

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }


  public List<Person> getObjects() {
    return objects;
  }

  public void setObjects(List<Person> objects) {
    this.objects = objects;
  }

  public Person getOneObject() {
    return oneObject;
  }

  public void setOneObject(Person oneObject) {
    this.oneObject = oneObject;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
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

  public Long getCitizenId() {
    return citizenId;
  }

  public void setCitizenId(Long citizenId) {
    this.citizenId = citizenId;
  }

  @Override
  public String toString() {
    return "SomeObject{" +
        "objects=" + objects +
        ", oneObject=" + oneObject +
        ", lastName='" + lastName + '\'' +
        ", organization='" + organization + '\'' +
        ", employeeId=" + employeeId +
        ", citizenId=" + citizenId +
        ", address=" + address +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Citizen that = (Citizen) o;
    return Objects.equals(objects, that.objects) &&
        Objects.equals(oneObject, that.oneObject) &&
        Objects.equals(lastName, that.lastName) &&
        Objects.equals(organization, that.organization) &&
        Objects.equals(employeeId, that.employeeId) &&
        Objects.equals(citizenId, that.citizenId) &&
        Objects.equals(address, that.address);
  }

  @Override
  public int hashCode() {
    return Objects.hash(objects, oneObject, lastName, organization, employeeId, citizenId, address);
  }
}
