package io.aditya.kam.convertor;

import io.aditya.kam.entity.CustomerOrderEntity;
import io.aditya.kam.dto.CustomerOrder;
import org.springframework.stereotype.Service;


@Service
public class CustomerOrderConvertor {
  public CustomerOrderEntity toEntity(CustomerOrder customerOrder) {
    return CustomerOrderEntity.builder()
        .orderID(customerOrder.getOrderID())
        .interactionID(customerOrder.getInteractionID())
        .transactionValue(customerOrder.getTransactionValue())
        .customerID(customerOrder.getCustomerID())
        .build();
  }

  public CustomerOrder toDTO(CustomerOrderEntity customerOrderEntity) {
    return CustomerOrder.builder()
        .orderID(customerOrderEntity.getOrderID())
        .interactionID(customerOrderEntity.getInteractionID())
        .transactionValue(customerOrderEntity.getTransactionValue())
        .customerID(customerOrderEntity.getCustomerID())
        .build();
  }
}
