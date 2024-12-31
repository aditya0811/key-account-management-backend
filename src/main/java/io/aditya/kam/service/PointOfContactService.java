package io.aditya.kam.service;

import io.aditya.kam.entity.PointOfContact;
import java.util.List;
import java.util.Optional;


public interface PointOfContactService {
  PointOfContact create(PointOfContact pointOfContact);
  Optional<PointOfContact> findById(String id);
  List<PointOfContact> listPointOfContacts();
}
