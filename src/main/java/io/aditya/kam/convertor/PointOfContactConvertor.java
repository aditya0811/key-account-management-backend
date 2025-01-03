package io.aditya.kam.convertor;

import io.aditya.kam.entity.PointOfContactEntity;
import io.aditya.kam.dto.PointOfContact;
import org.springframework.stereotype.Service;


@Service
public class PointOfContactConvertor {
  public PointOfContactEntity toEntity(PointOfContact pointOfContact) {
    return PointOfContactEntity.builder()
        .name(pointOfContact.getName())
        .role(pointOfContact.getRole())
        .contactInformation(pointOfContact.getContactInformation())
        .workingHours(pointOfContact.getWorkingHours())
        .customerID(pointOfContact.getCustomerID())
        .build();
  }

  public PointOfContact toModel(PointOfContactEntity pointOfContactEntity) {
    return PointOfContact.builder().pointOfContactID(pointOfContactEntity.getPointOfContactID())
        .name(pointOfContactEntity.getName())
        .role(pointOfContactEntity.getRole())
        .contactInformation(pointOfContactEntity.getContactInformation())
        .workingHours(pointOfContactEntity.getWorkingHours())
        .customerID(pointOfContactEntity.getCustomerID())
        .build();


  }
}
