package io.aditya.kam.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Interaction {

  Integer interactionID;
  Integer keyAccountManagerID;
  Integer customerID;
  Integer pointOfContactID;
  Boolean isConverted=false;
  String interactionTimestamp;
  String audioLink;
  Integer orderID;
  String interactionComment;
  Boolean isKeyAccountManagerChanged=false;
  Integer changedKeyAccountManagerID;
  Boolean isPointOfContactChanged=false;
  Integer changedPointOfContactID;
  Boolean isOrderPlaced=false;
  Integer transactionValue=0;


}
