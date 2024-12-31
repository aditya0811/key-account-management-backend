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
@Table(name="CustomerOrder")
public class CustomerOrderEntity {

  @Id
  String orderID;
  String customerID;
  String interactionID;
  Integer transactionValue;

}
