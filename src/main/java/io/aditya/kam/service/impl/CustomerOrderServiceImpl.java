package io.aditya.kam.service.impl;

import io.aditya.kam.entity.CustomerOrder;
import io.aditya.kam.entity.CustomerOrderEntity;
import io.aditya.kam.repository.CustomerOrderRepository;
import io.aditya.kam.service.CustomerOrderService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {
  private final CustomerOrderRepository customerOrderRepository;

  /**
   * Using a constructor based injection, as the variable is final, if we use setter based injection
   * the repository variable is not final
   * @param customerOrderRepository
   */
  @Autowired
  public CustomerOrderServiceImpl(final CustomerOrderRepository customerOrderRepository) {
    this.customerOrderRepository = customerOrderRepository;

  }

  @Override
  public CustomerOrder create(CustomerOrder customerOrder) {
    CustomerOrderEntity customerOrderEntity =
        customerOrderRepository.save(orderToEntityConversion(customerOrder));
    return entityToOrderConversion(customerOrderEntity);
  }

  @Override
  public Optional<CustomerOrder> findById(Integer id) {
    Optional<CustomerOrderEntity> foundOrderEntity
        = customerOrderRepository.findById(id);

    return foundOrderEntity.map(this::entityToOrderConversion);
  }

  @Override
  public List<CustomerOrder> listOrders() {
    final List<CustomerOrderEntity> foundOrders = customerOrderRepository.findAll();
    return foundOrders.stream()
        .map(this::entityToOrderConversion)
        .collect(Collectors.toList());
  }

  private CustomerOrderEntity orderToEntityConversion(CustomerOrder customerOrder) {
    return CustomerOrderEntity.builder()
        .orderID(customerOrder.getOrderID())
        .interactionID(customerOrder.getInteractionID())
        .transactionValue(customerOrder.getTransactionValue())
        .customerID(customerOrder.getCustomerID())
        .build();
  }

  private CustomerOrder entityToOrderConversion(CustomerOrderEntity customerOrderEntity) {
    return CustomerOrder.builder()
        .orderID(customerOrderEntity.getOrderID())
        .interactionID(customerOrderEntity.getInteractionID())
        .transactionValue(customerOrderEntity.getTransactionValue())
        .customerID(customerOrderEntity.getCustomerID())
        .build();
  }
}
