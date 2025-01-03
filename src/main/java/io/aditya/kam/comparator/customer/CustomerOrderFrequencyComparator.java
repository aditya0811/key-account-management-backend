package io.aditya.kam.comparator.customer;

import io.aditya.kam.dto.Customer;
import java.util.Comparator;


public class CustomerOrderFrequencyComparator implements Comparator<Customer> {
  @Override
  public int compare(Customer c1, Customer c2) {
    return (c1.getNumberOfOrders() - c2.getNumberOfOrders());
  }

  @Override
  public Comparator<Customer> reversed() {
    return Comparator.super.reversed();
  }
}
