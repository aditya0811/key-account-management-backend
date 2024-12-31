package io.aditya.kam.controller;


import io.aditya.kam.entity.Customer;
import io.aditya.kam.service.CustomerService;
import io.aditya.kam.service.KeyAccountManagerService;
import io.aditya.kam.service.PointOfContactService;
import io.aditya.kam.utils.ApplicationUtils;
import java.util.Collections;
import java.util.Comparator;
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
public class CustomerController {

  private final CustomerService customerService;
  private final KeyAccountManagerService keyAccountManagerService;
  private final PointOfContactService pointOfContactService;

  @Autowired
  public CustomerController(CustomerService customerService, KeyAccountManagerService keyAccountManagerService,
      PointOfContactService pointOfContactService) {
    this.customerService = customerService;
    this.keyAccountManagerService = keyAccountManagerService;
    this.pointOfContactService = pointOfContactService;
  }

  @PutMapping(path="/customers/{customerID}")
  public ResponseEntity<Customer> createAndUpdateCustomer(
      @PathVariable String customerID,
      @RequestBody final Customer customer) {
    //TODO we maynot need this, if request body, does not have id, query parameter need to have id.
    customer.setCustomerID(customerID);
    final boolean isCustomerExists = customerService.isCustomerIDExists(customer);
//    if (isCustomerExists) {
//      updateCustomer(customer, customerService.findById(customerID).get());
//    }


    final Customer savedCustomer = customerService.save(customer);
    if (isCustomerExists) {
      return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }
  }

  //TODO Non Restful, need to update this a) initial(first poc) (b) interaction (change in poc true, need to invoke endpoint, or just method will?)
  @PutMapping(path="/customers/{customerID}/update-point-of-contact")
  public ResponseEntity<Customer> updatePointOfContactID(
      @PathVariable String customerID,
      @RequestBody final Customer customer) {

    Optional<Customer> customerFromDB = customerService.findById(customerID);
    if (customerFromDB.isPresent()) {

      customerFromDB.get().setPointOfContactID(customer.getPointOfContactID());

      String nextMeetingTimestamp = ApplicationUtils.getDateWithFrequencyOfCalls(keyAccountManagerService,
          pointOfContactService, customerFromDB.get());
      customerFromDB.get().setNextCallScheduledTimestampInUTC(nextMeetingTimestamp);


      final Customer savedCustomer = customerService.save(customerFromDB.get());
      return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }
  }

  @GetMapping(path="/customers/{customerID}")
  public ResponseEntity<Customer> retrieveCustomer(@PathVariable String customerID) {
    final Optional<Customer> foundCustomer = customerService.findById(customerID);
    return foundCustomer
        .map(customer -> new ResponseEntity<>(customer, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping(path = "/customers")
  public ResponseEntity<List<Customer>> listCustomers() {
    return new ResponseEntity<List<Customer>>(customerService.listCustomers(), HttpStatus.OK);
  }

  //TODO may need to route this to KAM, inject service
  @GetMapping(path = "/key-account-managers/{keyAccountManagerID}/customers")
  public ResponseEntity<List<Customer>> listAllCustomersForKeyAccountManager(@PathVariable Integer keyAccountManagerID) {
    List<Customer> keyAccountManagerCustomers = customerService
        .listCustomers()
        .stream()
        .filter(customer -> Objects.equals(customer.getKeyAccountManagerID(), keyAccountManagerID))
        .toList();
    return new ResponseEntity<List<Customer>>(keyAccountManagerCustomers, HttpStatus.OK);
  }

  //TODO may need to route this to KAM, inject service
  @GetMapping(path = "/key-account-managers/{keyAccountManagerID}/{metric}/{count}/{descending}")
  public ResponseEntity<List<Customer>> listAllCustomersForKeyAccountManagerForMetric(@PathVariable Integer keyAccountManagerID,
      @PathVariable String metric, @PathVariable int count, @PathVariable boolean descending) {
    List<Customer> keyAccountManagerCustomers = new java.util.ArrayList<>(customerService.listCustomers().stream()
        .filter(customer -> Objects.equals(customer.getKeyAccountManagerID(), keyAccountManagerID)).toList());

    Collections.sort(keyAccountManagerCustomers, new Comparator<Customer>() {
      @Override
      public int compare(Customer a1, Customer a2) {
        if (metric.equals("frequency")) {
          return (a1.getNumberOfOrders() - a2.getNumberOfOrders()) * (descending ? -1 : 1);
        } else {
          return (a1.getTotalTransactionValue() - a2.getTotalTransactionValue()) * (descending ? -1 : 1);
        }
        //Add NOT FOUND METRIC ERROR HERE
      }
    });
    int keyAccountManagerCustomersCount= keyAccountManagerCustomers.size();
    return new ResponseEntity<List<Customer>>(keyAccountManagerCustomers.subList(0,
        Math.min(count,keyAccountManagerCustomersCount)), HttpStatus.OK);
  }

  @GetMapping(path = "/key-account-managers/{keyAccountManagerID}/interactions-scheduled-today")
  public ResponseEntity<List<Customer>> listAllCustomersScheduledForToday(@PathVariable Integer keyAccountManagerID) {
    List<Customer> keyAccountManagerCustomers = customerService.listCustomers().stream()
        .filter(customer -> Objects.equals(customer.getKeyAccountManagerID(), keyAccountManagerID) &&
                Objects.equals(customer.getNextCallScheduledTimestampInUTC().substring(0,10),
                    ApplicationUtils.getCurrentDate()))
        .toList();

    return new ResponseEntity<List<Customer>>(keyAccountManagerCustomers, HttpStatus.OK);
  }

//  private void updateCustomer(Customer customerFromBody, Customer existingCustomer) {
//    try {
//      ObjectMapper objectMapper = new ObjectMapper();
//      Map<String, Object> requestMap = objectMapper.convertValue(customerFromBody, new TypeReference<Map<String, Object>>() {});
//      for (String key : requestMap.keySet()) {
//
//      }
//
//
//    } catch (Exception exception) {
//
//    }
//
//
//  }
}
