package io.aditya.kam.service;

import io.aditya.kam.entity.CustomerOrder;
import java.util.List;
import java.util.Optional;


public interface CustomerOrderService {

  CustomerOrder create(CustomerOrder customerOrder);
  Optional<CustomerOrder> findById(String id);
  List<CustomerOrder> listOrders();
}
