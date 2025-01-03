package io.aditya.kam.service.impl;

import io.aditya.kam.convertor.PointOfContactConvertor;
import io.aditya.kam.dto.PointOfContact;
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

  @Autowired
  private PointOfContactConvertor pointOfContactConvertor;

  /**
   * Using a constructor based injection
   * @param pointOfContactRepository
   */
  @Autowired
  public PointOfContactServiceImpl(final PointOfContactRepository pointOfContactRepository) {
    this.pointOfContactRepository = pointOfContactRepository;

  }

  @Override
  public PointOfContact create(PointOfContact pointOfContact) {
    PointOfContactEntity pointOfContactEntity =
        pointOfContactRepository.save(pointOfContactConvertor.toEntity(pointOfContact));
    return pointOfContactConvertor.toModel(pointOfContactEntity);
  }

  @Override
  public Optional<PointOfContact> findById(Integer id) {
    Optional<PointOfContactEntity> foundPointOfContactEntity
        = pointOfContactRepository.findById(id);

    return foundPointOfContactEntity.map(pointOfContactEntity -> pointOfContactConvertor.toModel(pointOfContactEntity));
  }

  @Override
  public List<PointOfContact> listPointOfContacts() {
    final List<PointOfContactEntity> foundPointOfContacts = pointOfContactRepository.findAll();
    return foundPointOfContacts.stream()
        .map(pointOfContactEntity -> pointOfContactConvertor.toModel(pointOfContactEntity))
        .collect(Collectors.toList());
  }

}
