package io.aditya.kam.service;

import io.aditya.kam.dto.CustomerOrder;
import java.util.List;
import java.util.Optional;


public interface CustomerOrderService {

  CustomerOrder create(CustomerOrder customerOrder);
  Optional<CustomerOrder> findById(Integer id);
  List<CustomerOrder> listOrders();
}
