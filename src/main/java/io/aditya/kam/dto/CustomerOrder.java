package io.aditya.kam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerOrder {

  Integer orderID;
  Integer customerID;
  Integer interactionID;
  Integer transactionValue;
}
