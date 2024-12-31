package io.aditya.kam.service.impl;

import io.aditya.kam.entity.Customer;
import io.aditya.kam.entity.CustomerEntity;
import io.aditya.kam.enums.CustomerType;
import io.aditya.kam.enums.LeadStatus;
import io.aditya.kam.repository.CustomerRepository;
import io.aditya.kam.service.CustomerService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;

  /**
   * Using a constructor based injection, as the variable is final, if we use setter based injection
   * the repository variable is not final
   * @param customerRepository customerRepository
   */
  @Autowired
  public CustomerServiceImpl(final CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;

  }

  @Override
  public Customer save(Customer customer) {
    CustomerEntity customerEntity =
        customerRepository.save(customerToEntityConversion(customer));
    return entityToCustomerConversion(customerEntity);
  }

  @Override
  public Optional<Customer> findById(String id) {
    Optional<CustomerEntity> customerEntity
        = customerRepository.findById(id);

    return customerEntity.map(this::entityToCustomerConversion);
  }


  //This may need one more to take in key account manager query parameter, for getting customers specific to id
  @Override
  public List<Customer> listCustomers() {
    final List<CustomerEntity> foundCustomers = customerRepository.findAll();
    return foundCustomers.stream()
        .map(this::entityToCustomerConversion)
        .collect(Collectors.toList());
  }


  //TODO Add test 1:08
  @Override
  public boolean isCustomerIDExists(Customer customer) {
    return customerRepository.existsById(customer.getCustomerID());
  }


  //ADd request validation, just after getting customer from presentation layer
  private CustomerEntity customerToEntityConversion(Customer customer) {
    return CustomerEntity.builder().customerID(customer.getCustomerID())
        .name(customer.getName())
        .address(customer.getAddress())
        .customerType(String.valueOf(customer.getCustomerType()))
        .numberOfOrders(customer.getNumberOfOrders())
        .totalTransactionValue(customer.getTotalTransactionValue())
        .keyAccountManagerID(customer.getKeyAccountManagerID())
        .tags(customer.getTags())
        .pointOfContactID(customer.getPointOfContactID())
        .leadStatus(String.valueOf(customer.getLeadStatus()))
        .frequencyOfCallsInDays(customer.getFrequencyOfCallsInDays())
        .nextCallScheduledTimestamp(customer.getNextCallScheduledTimestamp())
        .lastCallScheduledTimestamp(customer.getLastCallScheduledTimestamp())
        .build();
  }

  private Customer entityToCustomerConversion(CustomerEntity customerEntity) {
    return Customer.builder().customerID(customerEntity.getCustomerID())
        .name(customerEntity.getName())
        .address(customerEntity.getAddress())
        .customerType(CustomerType.valueOf(customerEntity.getCustomerType()))
        .numberOfOrders(customerEntity.getNumberOfOrders())
        .totalTransactionValue(customerEntity.getTotalTransactionValue())
        .keyAccountManagerID(customerEntity.getKeyAccountManagerID())
        .tags(customerEntity.getTags())
        .pointOfContactID(customerEntity.getPointOfContactID())
        .leadStatus(LeadStatus.valueOf(customerEntity.getLeadStatus()))
        .frequencyOfCallsInDays(customerEntity.getFrequencyOfCallsInDays())
        .nextCallScheduledTimestamp(customerEntity.getNextCallScheduledTimestamp())
        .lastCallScheduledTimestamp(customerEntity.getLastCallScheduledTimestamp())
        .build();
  }
}
