package io.aditya.kam.controller;

import io.aditya.kam.entity.Customer;
import io.aditya.kam.entity.Interaction;
import io.aditya.kam.entity.CustomerOrder;
import io.aditya.kam.enums.LeadStatus;
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

  @PutMapping(path="/customers/{customerID}/interactions/{interactionID}")
  public ResponseEntity<Interaction> createInteraction(@PathVariable String interactionID,
      @PathVariable String customerID,
      @RequestBody final Interaction interaction) {
    //TODO we maynot need this, if request body, does not have id, query parameter need to have id.
    interaction.setInteractionID(interactionID);
    interaction.setCustomerID(customerID);
    interaction.setInteractionTimestamp(ApplicationUtils.getCurrentTimestamp());


    updateCustomerAndOrder(interaction);

    final Interaction savedInteraction = interactionService.create(interaction);
    //TODO Do we care if previous interaction details are not here, incase of just updating transaction? like poc will come null
    // in 2nd transaction BUGG, maybe fetch last interaction for the customer(last call tracked will also help and then copy those details)
    return new ResponseEntity<>(savedInteraction, HttpStatus.CREATED);
  }

//  //TODO Non Restful, need to update this a) initial(first poc) (b) interaction (change in poc true, need to invoke endpoint, or just method will?)
//  @PutMapping(path="/interactions/{interactionID}/update-point-of-contact")
//  public ResponseEntity<Interaction> updatePointOfContactID(
//      @PathVariable String interactionID,
//      @RequestBody final Interaction interaction) {
//
//    Optional<Interaction> interactionFromDB = interactionService.findById(interactionID);
//    if (interactionFromDB.isPresent()) {
//
//      interactionFromDB.get().setPointOfContactID(interaction.getPointOfContactID());
//
//      //TODO interactionFromDB.get().setNextCallScheduledTimestampInUTC while creating new interaction, next call 10days+current, and check poc id timestamp
//
//      final Interaction savedInteraction = interactionService.save(interactionFromDB.get());
//      return new ResponseEntity<>(savedInteraction, HttpStatus.OK);
//    } else {
//      return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
//    }
//  }

  @GetMapping(path="/customers/{customerID}/interactions/{interactionID}")
  public ResponseEntity<Interaction> retrieveInteraction(@PathVariable String interactionID,
      @PathVariable String customerID) {
    final Optional<Interaction> foundInteraction = interactionService.findById(interactionID);
    return foundInteraction
        .map(interaction -> new ResponseEntity<>(interaction, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }


  //TODO may need to route this to KAM, inject service
  @GetMapping(path = "/customers/{customerID}/interactions")
  public ResponseEntity<List<Interaction>> listAllInteractionsForKeyAccountManager(@PathVariable String customerID) {
    List<Interaction> customerInteractions = interactionService
        .listInteractions()
        .stream()
        .filter(interaction -> Objects.equals(interaction.getCustomerID(), customerID))
        .toList();
    return new ResponseEntity<List<Interaction>>(customerInteractions, HttpStatus.OK);
  }

  private void updateCustomerAndOrder(Interaction interaction) {
    Optional<Customer> optionalCustomer = customerService.findById(interaction.getCustomerID());
    if(optionalCustomer.isPresent()) {
      Customer customer = optionalCustomer.get();
      if (customer.getLeadStatus() == LeadStatus.PROSPECTIVE) {
        customer.setLeadStatus(LeadStatus.TRACKING);
      }
      if (interaction.getIsConverted()){
        customer.setLeadStatus(LeadStatus.CONVERTED);
      }

      if (interaction.getIsKeyAccountManagerChanged()){
        customer.setKeyAccountManagerID(interaction.getChangedKeyAccountManagerID());
      }

      if (interaction.getIsOrderPlaced()){
        customer.setNumberOfOrders(customer.getNumberOfOrders()+1);
        customer.setTotalTransactionValue(customer.getTotalTransactionValue()+interaction.getTransactionValue());
        CustomerOrder customerOrder = CustomerOrder.builder()
            .customerID(interaction.getCustomerID())
            .orderID(interaction.getOrderID())
            .transactionValue(interaction.getTransactionValue())
            .interactionID(interaction.getInteractionID())
            .build();
        customerOrderService.create(customerOrder);
      }

      //poc update before call update

      //last call is updated with current call
      String lastCall = customer.getNextCallScheduledTimestampInUTC();
      customer.setLastCallScheduledTimestampInUTC(lastCall);
      String nextMeetingTimestamp = ApplicationUtils.getDateWithFrequencyOfCalls(keyAccountManagerService,
          pointOfContactService, customer);
      customer.setNextCallScheduledTimestampInUTC(nextMeetingTimestamp);


      //final save customer
      customerService.save(customer);
    }


  }
}
