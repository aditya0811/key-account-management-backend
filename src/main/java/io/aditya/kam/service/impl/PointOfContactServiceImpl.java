package io.aditya.kam.service.impl;

import io.aditya.kam.entity.PointOfContact;
import io.aditya.kam.entity.PointOfContactEntity;
import io.aditya.kam.repository.PointOfContactRepository;
import io.aditya.kam.service.PointOfContactService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PointOfContactServiceImpl implements PointOfContactService {
  private final PointOfContactRepository pointOfContactRepository;

  /**
   * Using a constructor based injection, as the variable is final, if we use setter based injection
   * the repository variable is not final
   * @param pointOfContactRepository
   */
  @Autowired
  public PointOfContactServiceImpl(final PointOfContactRepository pointOfContactRepository) {
    this.pointOfContactRepository = pointOfContactRepository;

  }

  @Override
  public PointOfContact create(PointOfContact pointOfContact) {
    PointOfContactEntity pointOfContactEntity =
        pointOfContactRepository.save(pointOfContactToEntityConversion(pointOfContact));
    return entityToPointOfContactConversion(pointOfContactEntity);
  }

  @Override
  public Optional<PointOfContact> findById(Integer id) {
    Optional<PointOfContactEntity> foundPointOfContactEntity
        = pointOfContactRepository.findById(id);

    return foundPointOfContactEntity.map(this::entityToPointOfContactConversion);
  }

  @Override
  public List<PointOfContact> listPointOfContacts() {
    final List<PointOfContactEntity> foundPointOfContacts = pointOfContactRepository.findAll();
    return foundPointOfContacts.stream()
        .map(this::entityToPointOfContactConversion)
        .collect(Collectors.toList());
  }

  private PointOfContactEntity pointOfContactToEntityConversion(PointOfContact pointOfContact) {
    return PointOfContactEntity.builder()
        .name(pointOfContact.getName())
        .role(pointOfContact.getRole())
        .contactInformation(pointOfContact.getContactInformation())
        .workingHours(pointOfContact.getWorkingHours())
        .customerID(pointOfContact.getCustomerID())
        .build();
  }

  private PointOfContact entityToPointOfContactConversion(PointOfContactEntity pointOfContactEntity) {
    return PointOfContact.builder().pointOfContactID(pointOfContactEntity.getPointOfContactID())
        .name(pointOfContactEntity.getName())
        .role(pointOfContactEntity.getRole())
        .contactInformation(pointOfContactEntity.getContactInformation())
        .workingHours(pointOfContactEntity.getWorkingHours())
        .customerID(pointOfContactEntity.getCustomerID())
        .build();
  }
}
