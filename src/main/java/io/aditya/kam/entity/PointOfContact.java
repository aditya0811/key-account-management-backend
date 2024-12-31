package io.aditya.kam.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointOfContact {
  //Maybe change thi everywhere to id, to make this re-usable
  String pointOfContactID;
  String customerID;
  String name;
  String role;

  //Should have country code
  String contactInformation;

  String workingHours;

}


