package io.aditya.kam.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Interaction {

  String interactionID;
  Integer keyAccountManagerID;
  String customerID;
  String pointOfContactID;
  Boolean isConverted=false;
  String interactionTimestamp;
  String audioLink;
  String orderID;
  String interactionComment;
  Boolean isKeyAccountManagerChanged=false;
  Integer changedKeyAccountManagerID;
  Boolean isPointOfContactChanged=false;
  Integer changedPointOfContactID;
  Boolean isOrderPlaced=false;
  Integer transactionValue=0;


}
