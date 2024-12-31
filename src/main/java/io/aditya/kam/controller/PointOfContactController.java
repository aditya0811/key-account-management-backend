package io.aditya.kam.controller;

import io.aditya.kam.entity.Customer;
import io.aditya.kam.entity.PointOfContact;
import io.aditya.kam.service.PointOfContactService;
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
public class PointOfContactController {

  private final PointOfContactService pointOfContactService;

  @Autowired
  public PointOfContactController(PointOfContactService pointOfContactService) {
    this.pointOfContactService = pointOfContactService;
  }

  @PutMapping(path="/v1/key-account-managers/{keyAccountManagerID}/customers/{customerID}/point-of-contacts/{pointOfContactID}")
  public ResponseEntity<PointOfContact> createPointOfContact(
      @PathVariable String customerID,
      @PathVariable String pointOfContactID,
      @RequestBody final PointOfContact pointOfContact) {
    //TODO Do we need these, if request body has it already
    pointOfContact.setPointOfContactID(pointOfContactID);
    pointOfContact.setCustomerID(customerID);
    final PointOfContact savedPointOfContact = pointOfContactService.create(pointOfContact);
    ResponseEntity<PointOfContact> response =
        new ResponseEntity<>(savedPointOfContact, HttpStatus.CREATED);
    return response;
  }

  @GetMapping(path="/v1/key-account-managers/{keyAccountManagerID}/customers/{customerID}/point-of-contacts/{pointOfContactID}")
  public ResponseEntity<PointOfContact> retrievePointOfContact(@PathVariable String pointOfContactID) {
    final Optional<PointOfContact> foundPointOfContact = pointOfContactService.findById(pointOfContactID);
    return foundPointOfContact
        .map(pointOfContact -> new ResponseEntity<>(pointOfContact, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping(path = "/v1/key-account-managers/{keyAccountManagerID}/customers/{customerID}/point-of-contacts")
  public ResponseEntity<List<PointOfContact>> listPointOfContacts(@PathVariable String customerID) {
    List<PointOfContact> customerPointOfContact = pointOfContactService
        .listPointOfContacts()
        .stream()
        .filter(pointOfContact -> Objects.equals(pointOfContact.getCustomerID(), customerID))
        .toList();
    return new ResponseEntity<List<PointOfContact>>(customerPointOfContact, HttpStatus.OK);
  }
  
  
  
}
