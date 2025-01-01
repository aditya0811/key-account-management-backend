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
@Table(name="Interaction")
public class InteractionEntity {


  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  Integer interactionID;
  Integer keyAccountManagerID;
  Integer customerID;
  Integer pointOfContactID;
  Boolean isConverted;
  String interactionTimestamp;
  String audioLink;
  Integer orderID;
  String interactionComment;
  Boolean isKeyAccountManagerChanged;
  Integer changedKeyAccountManagerID;
  Boolean isPointOfContactChanged;
  Integer changedPointOfContactID;
  Boolean isOrderPlaced;
  Integer transactionValue;
}
