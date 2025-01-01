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
@Table(name="Customer")
public class CustomerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  Integer customerID;
  String name;
  String address;
  String customerType;
  Integer numberOfOrders;
  Integer totalTransactionValue;
  Integer keyAccountManagerID;
  String tags;
  Integer pointOfContactID;
  String leadStatus;
  Integer frequencyOfCallsInDays;
  String nextCallScheduledTimestamp;
  String lastCallScheduledTimestamp;
}
