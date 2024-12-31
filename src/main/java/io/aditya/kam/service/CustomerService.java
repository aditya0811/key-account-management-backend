package io.aditya.kam.service;

import io.aditya.kam.entity.Customer;
import io.aditya.kam.entity.KeyAccountManager;
import java.util.List;
import java.util.Optional;


public interface CustomerService {
  Customer save(Customer customer);
  Optional<Customer> findById(String id);
  List<Customer> listCustomers();
  boolean isCustomerIDExists(Customer customer);
}
