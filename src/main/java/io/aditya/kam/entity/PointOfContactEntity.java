package io.aditya.kam.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="PointOfContact")
public class PointOfContactEntity {

  //TODO With this poc id cannot be same for two different restaurant, this is bug!!, can be same
  @Id
  String pointOfContactID;
  String customerID;
  String name;
  String role;

  //Should have country code
  String contactInformation;

  String workingHours;
}
