package io.aditya.kam.entity;

import io.aditya.kam.enums.CustomerType;
import io.aditya.kam.enums.LeadStatus;
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

  String workingHoursInUTC;

}


