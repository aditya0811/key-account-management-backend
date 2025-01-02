package io.aditya.kam.model;

import io.aditya.kam.builder.KeyAccountManagerBuilder;
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
public class KeyAccountManager {
  Integer keyAccountManagerID;
  String name;
  String role;

  //Should have country code
  String contactInformation;

  String workingHours;
}
