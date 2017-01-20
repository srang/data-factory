package com.github.datafactory.resources;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class SubObject {
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
}
