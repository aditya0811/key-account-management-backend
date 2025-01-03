package io.aditya.kam.service;

import io.aditya.kam.dto.PointOfContact;
import java.util.List;
import java.util.Optional;


public interface PointOfContactService {
  PointOfContact create(PointOfContact pointOfContact);
  Optional<PointOfContact> findById(Integer id);
  List<PointOfContact> listPointOfContacts();
}
