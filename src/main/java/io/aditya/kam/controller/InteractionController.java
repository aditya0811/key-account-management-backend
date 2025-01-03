package io.aditya.kam.controller;

import io.aditya.kam.exception.CustomerNotFoundException;
import io.aditya.kam.exception.PointOfContactNotFoundException;
import io.aditya.kam.model.Customer;
import io.aditya.kam.model.Interaction;
import io.aditya.kam.service.CustomerService;
import io.aditya.kam.service.InteractionService;
import io.aditya.kam.service.CustomerOrderService;
import io.aditya.kam.service.KeyAccountManagerService;
import io.aditya.kam.service.PointOfContactService;
import io.aditya.kam.utils.ApplicationUtils;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class InteractionController {

  private final InteractionService interactionService;
  private final CustomerService customerService;
  private final CustomerOrderService customerOrderService;

  private final KeyAccountManagerService keyAccountManagerService;
  private final PointOfContactService pointOfContactService;

  @Autowired
  public InteractionController(InteractionService interactionService, CustomerService customerService,
      CustomerOrderService customerOrderService, KeyAccountManagerService keyAccountManagerService,
      PointOfContactService pointOfContactService) {
    this.interactionService = interactionService;
    this.customerService = customerService;
    this.customerOrderService = customerOrderService;
    this.keyAccountManagerService = keyAccountManagerService;
    this.pointOfContactService = pointOfContactService;
  }

  @PostMapping(path="/v1/key-account-managers/{keyAccountManagerID}/customers/{customerID}/interactions")
  public ResponseEntity<Interaction> createInteraction(@PathVariable Integer customerID,
      @PathVariable Integer keyAccountManagerID,
      @RequestBody final Interaction interaction ) throws PointOfContactNotFoundException, CustomerNotFoundException {
    interaction.setCustomerID(customerID);
    interaction.setInteractionTimestamp(ApplicationUtils.getCurrentTimestamp());
    interaction.setKeyAccountManagerID(keyAccountManagerID);

    Optional<Customer> foundCustomer = customerService.findById(customerID);
    if (foundCustomer.isEmpty()) {
     throw new CustomerNotFoundException("Customer does not exists");
    }
    if (foundCustomer.get().getPointOfContactID() == null) {
      throw new PointOfContactNotFoundException("Point of contact does not exists for this customer");
    }
    interaction.setPointOfContactID(foundCustomer.get().getPointOfContactID());

    ApplicationUtils.updateCustomerAndOrder(interaction, customerService, customerOrderService,
        keyAccountManagerService, pointOfContactService);

    final Interaction savedInteraction = interactionService.create(interaction);
    //TODO Do we care if previous interaction details are not here, incase of just updating transaction? like poc will come null
    // in 2nd transaction BUGG, maybe fetch last interaction for the customer(last call tracked will also help and then copy those details)
    return new ResponseEntity<>(savedInteraction, HttpStatus.CREATED);
  }

  @GetMapping(path="/v1/key-account-managers/{keyAccountManagerID}/customers/{customerID}/interactions/{interactionID}")
  public ResponseEntity<Interaction> retrieveInteraction(@PathVariable Integer interactionID,
      @PathVariable String customerID) {
    final Optional<Interaction> foundInteraction = interactionService.findById(interactionID);
    return foundInteraction
        .map(interaction -> new ResponseEntity<>(interaction, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }


  @GetMapping(path = "/v1/key-account-managers/{keyAccountManagerID}/customers/{customerID}/interactions")
  public ResponseEntity<List<Interaction>> listAllInteractionsForCustomer(@PathVariable Integer customerID) {
    List<Interaction> customerInteractions = interactionService
        .listInteractions()
        .stream()
        .filter(interaction -> Objects.equals(interaction.getCustomerID(), customerID))
        .toList();
    return new ResponseEntity<List<Interaction>>(customerInteractions, HttpStatus.OK);
  }

}
