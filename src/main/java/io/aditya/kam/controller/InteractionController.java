package io.aditya.kam.controller;

import io.aditya.kam.entity.Customer;
import io.aditya.kam.entity.Interaction;
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
import org.springframework.web.bind.annotation.PutMapping;
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

  @PutMapping(path="/v1/key-account-managers/{keyAccountManagerID}/customers/{customerID}/interactions/{interactionID}")
  public ResponseEntity<Interaction> createInteraction(@PathVariable String interactionID,
      @PathVariable String customerID,
      @PathVariable Integer keyAccountManagerID,
      @RequestBody final Interaction interaction) {
    //TODO we maynot need this, if request body, does not have id, query parameter need to have id.
    interaction.setInteractionID(interactionID);
    interaction.setCustomerID(customerID);
    interaction.setInteractionTimestamp(ApplicationUtils.getCurrentTimestamp());
    interaction.setKeyAccountManagerID(keyAccountManagerID);
    //TODO add checks for .get
    Optional<Customer> foundCustomer = customerService.findById(customerID);
    interaction.setPointOfContactID(foundCustomer.get().getPointOfContactID());

    ApplicationUtils.updateCustomerAndOrder(interaction, customerService, customerOrderService,
        keyAccountManagerService, pointOfContactService);

    final Interaction savedInteraction = interactionService.create(interaction);
    //TODO Do we care if previous interaction details are not here, incase of just updating transaction? like poc will come null
    // in 2nd transaction BUGG, maybe fetch last interaction for the customer(last call tracked will also help and then copy those details)
    return new ResponseEntity<>(savedInteraction, HttpStatus.CREATED);
  }
  //TODO Non Restful, need to update this a) initial(first poc) (b) interaction (change in poc true, need to invoke endpoint, or just method will?)


  @GetMapping(path="/v1/key-account-managers/{keyAccountManagerID}/customers/{customerID}/interactions/{interactionID}")
  public ResponseEntity<Interaction> retrieveInteraction(@PathVariable String interactionID,
      @PathVariable String customerID) {
    final Optional<Interaction> foundInteraction = interactionService.findById(interactionID);
    return foundInteraction
        .map(interaction -> new ResponseEntity<>(interaction, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }


  //TODO may need to route this to KAM, inject service
  @GetMapping(path = "/v1/key-account-managers/{keyAccountManagerID}/customers/{customerID}/interactions")
  public ResponseEntity<List<Interaction>> listAllInteractionsForKeyAccountManager(@PathVariable String customerID) {
    List<Interaction> customerInteractions = interactionService
        .listInteractions()
        .stream()
        .filter(interaction -> Objects.equals(interaction.getCustomerID(), customerID))
        .toList();
    return new ResponseEntity<List<Interaction>>(customerInteractions, HttpStatus.OK);
  }


}
