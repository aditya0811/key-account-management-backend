package io.aditya.kam.entity;
//JSK

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The goal here is to retrieve this entire object from database. Each one of this represents column
 * in DB
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="KeyAccountManager")
public class KeyAccountManagerEntity {

  @Id
  Integer keyAccountManagerID;
  String name;
  String role;

  //Should have country code
  String contactInformation;

  String workingHours;


}
