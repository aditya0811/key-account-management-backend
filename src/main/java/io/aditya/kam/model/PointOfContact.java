package io.aditya.kam.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointOfContact {
  Integer pointOfContactID;
  Integer customerID;
  String name;
  String role;

  //Should have country code
  String contactInformation;

  String workingHours;

}


