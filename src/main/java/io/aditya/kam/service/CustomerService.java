package io.aditya.kam.service;

import io.aditya.kam.dto.Customer;
import java.util.List;
import java.util.Optional;


public interface CustomerService {
  Customer save(Customer customer);

  Customer update(Customer customer);
  Optional<Customer> findById(Integer id);
  List<Customer> listCustomers();
  boolean isCustomerIDExists(Customer customer);
}
