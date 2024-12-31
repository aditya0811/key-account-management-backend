package io.aditya.kam.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerOrder {

  String orderID;
  String customerID;
  String interactionID;
  Integer transactionValue;
}
