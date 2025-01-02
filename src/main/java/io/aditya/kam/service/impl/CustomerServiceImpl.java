package io.aditya.kam.service.impl;

import io.aditya.kam.convertor.CustomerConvertor;
import io.aditya.kam.model.Customer;
import io.aditya.kam.entity.CustomerEntity;
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

  @Autowired
  private CustomerConvertor customerConvertor;

  /**
   * Using a constructor based injection
   * @param customerRepository customerRepository
   */
  @Autowired
  public CustomerServiceImpl(final CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;

  }

  @Override
  public Customer save(Customer customer) {
    CustomerEntity customerEntity =
        customerRepository.save(customerConvertor.toEntity(customer));
    return customerConvertor.toModel(customerEntity);
  }

  @Override
  public Customer update(Customer customer) {

    CustomerEntity customerEntityToUpdate = customerConvertor.toEntity(customer);
    customerEntityToUpdate.setCustomerID(customer.getCustomerID());
    customerRepository.save(customerEntityToUpdate);

    return customerConvertor.toModel(customerEntityToUpdate);
  }

  @Override
  public Optional<Customer> findById(Integer id) {
    Optional<CustomerEntity> foundCustomerEntity
        = customerRepository.findById(id);
    return foundCustomerEntity.map(customerEntity -> customerConvertor.toModel(customerEntity));
  }


  //This may need one more to take in key account manager query parameter, for getting customers specific to id
  @Override
  public List<Customer> listCustomers() {
    final List<CustomerEntity> foundCustomers = customerRepository.findAll();
    return foundCustomers.stream()
        .map(customerEntity -> customerConvertor.toModel(customerEntity))
        .collect(Collectors.toList());
  }


  //TODO Add test 1:08
  @Override
  public boolean isCustomerIDExists(Customer customer) {
    return customerRepository.existsById(customer.getCustomerID());
  }
}
