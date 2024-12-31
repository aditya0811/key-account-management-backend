package io.aditya.kam.entity;

import io.aditya.kam.enums.LeadStatus;
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
@Table(name="Customer")
public class CustomerEntity {

  @Id
  String customerID;
  String name;
  String address;
  String customerType;
  Integer numberOfOrders;
  Integer totalTransactionValue;
  Integer keyAccountManagerID;
  String tags;
  String pointOfContactID;
  String leadStatus;
  Integer frequencyOfCallsInDays;
  String nextCallScheduledTimestampInUTC;
  String lastCallScheduledTimestampInUTC;
}
