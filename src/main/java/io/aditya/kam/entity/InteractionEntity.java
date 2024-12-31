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
@Table(name="Interaction")
public class InteractionEntity {


  @Id
  String interactionID;
  Integer keyAccountManagerID;
  String customerID;
  String pointOfContactID;
  Boolean isConverted;
  String interactionTimestamp;
  String audioLink;
  String orderID;
  String interactionComment;
  Boolean isKeyAccountManagerChanged;
  Integer changedKeyAccountManagerID;
  Boolean isPointOfContactChanged;
  Integer changedPointOfContactID;
  Boolean isOrderPlaced;
  Integer transactionValue;
}
