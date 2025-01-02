package io.aditya.kam.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
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

  @NotNull
  String role;

  String contactInformation;

  @NotNull
  @Pattern(regexp = "(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]\\s-\\s(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]\\s[A-Za-z][A-Za-z][A-Za-z]",
      message = "Working hours should be in HH:mm - HH:mm XYZ format")
  String workingHours;
}
