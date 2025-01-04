package io.aditya.kam.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  Integer pointOfContactID;
  Integer customerID;
  String name;
  String role;
  String contactInformation;
  String workingHours;
}
