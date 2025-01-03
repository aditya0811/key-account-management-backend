package io.aditya.kam.controller;


import io.aditya.kam.comparator.customer.CustomerComparatorFactory;
import io.aditya.kam.exception.CustomerNotFoundException;
import io.aditya.kam.model.Customer;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CustomerController {

  private final CustomerService customerService;
  private final KeyAccountManagerService keyAccountManagerService;
  private final PointOfContactService pointOfContactService;

  @Autowired
  private CustomerComparatorFactory customerComparatorFactory;

  @Autowired
  public CustomerController(CustomerService customerService, KeyAccountManagerService keyAccountManagerService,
      PointOfContactService pointOfContactService) {
    this.customerService = customerService;
    this.keyAccountManagerService = keyAccountManagerService;
    this.pointOfContactService = pointOfContactService;
  }

  @PostMapping(path="/v1/key-account-managers/{keyAccountManagerID}/customers")
  public ResponseEntity<Customer> createCustomer(@PathVariable Integer keyAccountManagerID,
      @RequestBody final Customer customer) {
    customer.setKeyAccountManagerID(keyAccountManagerID);
    final Customer savedCustomer = customerService.save(customer);
    return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
  }

  @PutMapping(path="/v1/key-account-managers/{keyAccountManagerID}/customers/{customerID}")
  public ResponseEntity<Customer> updateCustomer (@PathVariable Integer customerID,
      @PathVariable Integer keyAccountManagerID,
      @RequestBody final Customer customer) throws CustomerNotFoundException {
    final boolean isCustomerExists = customerService.isCustomerIDExists(customer);
    final Customer savedCustomer = customerService.update(customer);
    if (isCustomerExists) {
      return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
    } else {
      throw new CustomerNotFoundException("Customer does not exists");
    }
  }

  @GetMapping(path="/v1/key-account-managers/{keyAccountManagerID}/customers/{customerID}")
  public ResponseEntity<Customer> retrieveCustomer(@PathVariable Integer customerID) {
    final Optional<Customer> foundCustomer = customerService.findById(customerID);
    return foundCustomer
        .map(customer -> new ResponseEntity<>(customer, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }


  @GetMapping(path = "/v1/key-account-managers/{keyAccountManagerID}/customers")
  public ResponseEntity<List<Customer>> listAllCustomersForKeyAccountManager(@PathVariable Integer keyAccountManagerID) {
    List<Customer> keyAccountManagerCustomers = customerService
        .listCustomers()
        .stream()
        .filter(customer -> Objects.equals(customer.getKeyAccountManagerID(), keyAccountManagerID))
        .toList();
    return new ResponseEntity<List<Customer>>(keyAccountManagerCustomers, HttpStatus.OK);
  }

  //TODO Non Restful, need to update this a) initial(first poc) (b) interaction (change in poc true, need to invoke endpoint, or just method will?)
  @PutMapping(path="/v1/key-account-managers/{keyAccountManagerID}/customers/{customerID}/update-point-of-contact")
  public ResponseEntity<Customer> updatePointOfContactID(
      @PathVariable Integer customerID,
      @RequestBody final Customer customer) throws CustomerNotFoundException{

    Optional<Customer> customerFromDB = customerService.findById(customerID);
    if (customerFromDB.isPresent()) {

      customerFromDB.get().setPointOfContactID(customer.getPointOfContactID());

      String nextMeetingTimestamp = ApplicationUtils.getDateWithFrequencyOfCalls(keyAccountManagerService,
          pointOfContactService, customerFromDB.get());
      customerFromDB.get().setNextCallScheduledTimestamp(nextMeetingTimestamp);


      final Customer savedCustomer = customerService.update(customerFromDB.get());
      return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
    } else {
      throw new CustomerNotFoundException("Customer does not exists");
    }
  }



  @GetMapping(path = "/v1/key-account-managers/{keyAccountManagerID}/customers/{metric}/{count}/{descending}")
  public ResponseEntity<List<Customer>> listAllCustomersForKeyAccountManagerForMetric(@PathVariable Integer keyAccountManagerID,
      @PathVariable String metric, @PathVariable int count, @PathVariable boolean descending) {
    List<Customer> keyAccountManagerCustomers = new java.util.ArrayList<>(customerService
        .listCustomers().stream()
        .filter(customer -> Objects.equals(customer.getKeyAccountManagerID(), keyAccountManagerID))
        .toList());

    Comparator<Customer> comparator = customerComparatorFactory.getComparator(metric, descending);

    Collections.sort(keyAccountManagerCustomers, comparator);
    // TODO Keeping this for explanation, not exactly factory design, since we did not bring any abstraction, and creating class.
    // However we have reduced the responsibility here, although factory still needs to be modified, but without touching this class

//    Collections.sort(keyAccountManagerCustomers, new Comparator<Customer>() {
//      @Override
//      public int compare(Customer a1, Customer a2) {
//        if (metric.equals("frequency")) {
//          return (a1.getNumberOfOrders() - a2.getNumberOfOrders()) * (descending ? -1 : 1);
//        } else {
//          return (a1.getTotalTransactionValue() - a2.getTotalTransactionValue()) * (descending ? -1 : 1);
//        }
//        //Add NOT FOUND METRIC ERROR HERE
//      }
//    });
    int keyAccountManagerCustomersCount = keyAccountManagerCustomers.size();
    return new ResponseEntity<List<Customer>>(keyAccountManagerCustomers.subList(0,
        Math.min(count,keyAccountManagerCustomersCount)), HttpStatus.OK);
  }

  @GetMapping(path = "/v1/key-account-managers/{keyAccountManagerID}/customers/interactions-scheduled-today")
  public ResponseEntity<List<Customer>> listAllCustomersScheduledForToday(@PathVariable Integer keyAccountManagerID) {
    List<Customer> keyAccountManagerCustomers = customerService.listCustomers().stream()
        .filter(customer -> Objects.equals(customer.getKeyAccountManagerID(), keyAccountManagerID) &&
                Objects.equals(customer.getNextCallScheduledTimestamp().substring(0,10),
                    ApplicationUtils.getCurrentDate()))
        .toList();

    return new ResponseEntity<List<Customer>>(keyAccountManagerCustomers, HttpStatus.OK);
  }

}
