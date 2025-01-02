package io.aditya.kam.convertor;

import io.aditya.kam.entity.CustomerEntity;
import io.aditya.kam.enums.CustomerType;
import io.aditya.kam.enums.LeadStatus;
import io.aditya.kam.model.Customer;
import org.springframework.stereotype.Service;


@Service("customerConvertor")
public class CustomerConvertor {
 
  public CustomerEntity toEntity(Customer customer) {
    return CustomerEntity.builder()
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


  public Customer toModel(CustomerEntity customerEntity) {
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
