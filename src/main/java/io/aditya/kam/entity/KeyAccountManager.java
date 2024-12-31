package io.aditya.kam.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Here we can add fields that are required by service layer, and not directly persisted to database
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeyAccountManager {
  Integer keyAccountManagerID;
  String name;
  String role;

  //Should have country code
  String contactInformation;

  String workingHoursInUTC;
}
