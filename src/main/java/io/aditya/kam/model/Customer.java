package io.aditya.kam.model;

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
public class Customer {
  Integer customerID;
  String name;
  String address;
  CustomerType customerType;
  Integer numberOfOrders = 0;
  Integer totalTransactionValue = 0;
  Integer keyAccountManagerID;
  String tags;
  Integer pointOfContactID;
  LeadStatus leadStatus = LeadStatus.PROSPECTIVE;
  Integer frequencyOfCallsInDays = 10;
  String nextCallScheduledTimestamp;
  String lastCallScheduledTimestamp;

}
