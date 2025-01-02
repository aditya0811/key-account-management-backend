package io.aditya.kam.comparator.customer;

import io.aditya.kam.model.Customer;
import java.util.Comparator;


public class CustomerTPVComparator implements Comparator<Customer> {
  @Override
  public int compare(Customer c1, Customer c2) {
    return (c1.getTotalTransactionValue() - c2.getTotalTransactionValue());
  }

  @Override
  public Comparator<Customer> reversed() {
    return Comparator.super.reversed();
  }
}
