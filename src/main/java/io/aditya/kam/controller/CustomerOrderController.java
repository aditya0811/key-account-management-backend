package io.aditya.kam.controller;

import io.aditya.kam.dto.CustomerOrder;
import io.aditya.kam.service.CustomerOrderService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CustomerOrderController {
  private final CustomerOrderService customerOrderService;

  @Autowired
  public CustomerOrderController(CustomerOrderService customerOrderService) {
    this.customerOrderService = customerOrderService;
  }

  @PutMapping(path="/v1/key-account-managers/{keyAccountManagerID}/customers/{customerID}/orders/{orderID}")
  public ResponseEntity<CustomerOrder> createOrder(@PathVariable Integer orderID,
      @PathVariable Integer customerID,
      @RequestBody final CustomerOrder customerOrder) {
    customerOrder.setOrderID(orderID);
    customerOrder.setCustomerID(customerID);

    final CustomerOrder savedCustomerOrder = customerOrderService.create(customerOrder);
    return new ResponseEntity<>(savedCustomerOrder, HttpStatus.CREATED);
  }

  @GetMapping(path="/v1/key-account-managers/{keyAccountManagerID}/customers/{customerID}/orders/{orderID}")
  public ResponseEntity<CustomerOrder> retrieveOrder(@PathVariable Integer orderID) {
    final Optional<CustomerOrder> foundOrder = customerOrderService.findById(orderID);
    return foundOrder
        .map(customerOrder -> new ResponseEntity<>(customerOrder, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping(path = "/v1/key-account-managers/{keyAccountManagerID}/customers/{customerID}/orders")
  public ResponseEntity<List<CustomerOrder>> listAllOrdersForCustomer(@PathVariable Integer customerID) {
    List<CustomerOrder> customerCustomerOrders = customerOrderService
        .listOrders()
        .stream()
        .filter(customerOrder -> Objects.equals(customerOrder.getCustomerID(), customerID))
        .toList();
    return new ResponseEntity<List<CustomerOrder>>(customerCustomerOrders, HttpStatus.OK);
  }
}
