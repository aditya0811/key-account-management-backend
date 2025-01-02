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
public class CustomerOrderEntity  {

  @Id
  Integer orderID;
  Integer customerID;
  Integer interactionID;
  Integer transactionValue;

}
