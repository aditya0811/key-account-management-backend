package io.aditya.kam.service.impl;

import io.aditya.kam.convertor.CustomerOrderConvertor;
import io.aditya.kam.model.CustomerOrder;
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

  @Autowired
  private CustomerOrderConvertor customerOrderConvertor;

  /**
   * Using a constructor based injection
   * @param customerOrderRepository
   */
  @Autowired
  public CustomerOrderServiceImpl(final CustomerOrderRepository customerOrderRepository) {
    this.customerOrderRepository = customerOrderRepository;

  }

  @Override
  public CustomerOrder create(CustomerOrder customerOrder) {
    CustomerOrderEntity customerOrderEntity =
        customerOrderRepository.save(customerOrderConvertor.toEntity(customerOrder));
    return customerOrderConvertor.toModel(customerOrderEntity);
  }

  @Override
  public Optional<CustomerOrder> findById(Integer id) {
    Optional<CustomerOrderEntity> foundOrderEntity
        = customerOrderRepository.findById(id);

    return foundOrderEntity.map(orderEntity -> customerOrderConvertor.toModel(orderEntity));
  }

  @Override
  public List<CustomerOrder> listOrders() {
    final List<CustomerOrderEntity> foundOrders = customerOrderRepository.findAll();
    return foundOrders.stream()
        .map(orderEntity -> customerOrderConvertor.toModel(orderEntity))
        .collect(Collectors.toList());
  }

}
