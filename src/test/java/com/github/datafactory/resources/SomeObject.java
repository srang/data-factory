package com.github.datafactory.resources;

import lombok.Data;

import java.util.List;

@Data
public class SomeObject extends SuperObject{
  List<SubObject> objects;
  SubObject oneObject;
  String lastName;
  String organization;
  Integer employeeId;
  Long citizenId;

}
