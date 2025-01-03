package io.aditya.kam.service.impl;

import io.aditya.kam.convertor.InteractionConvertor;
import io.aditya.kam.dto.Interaction;
import io.aditya.kam.entity.InteractionEntity;
import io.aditya.kam.repository.InteractionRepository;
import io.aditya.kam.service.InteractionService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InteractionServiceImpl implements InteractionService {

  private final InteractionRepository interactionRepository;

  @Autowired
  private InteractionConvertor interactionConvertor;

  /**
   * Using a constructor based injection
   * @param interactionRepository
   */
  @Autowired
  public InteractionServiceImpl(final InteractionRepository interactionRepository) {
    this.interactionRepository = interactionRepository;

  }

  @Override
  public Interaction create(Interaction interaction) {
    InteractionEntity interactionEntity =
        interactionRepository.save(interactionConvertor.toEntity(interaction));
    return interactionConvertor.toDTO(interactionEntity);
  }

  @Override
  public Optional<Interaction> findById(Integer id) {
    Optional<InteractionEntity> foundInteractionEntity
        = interactionRepository.findById(id);

    return foundInteractionEntity.map(interactionEntity -> interactionConvertor.toDTO(interactionEntity));

  }

  @Override
  public List<Interaction> listInteractions() {
    final List<InteractionEntity> foundInteractions = interactionRepository.findAll();
    return foundInteractions.stream()
        .map(interactionEntity -> interactionConvertor.toDTO(interactionEntity))
        .collect(Collectors.toList());
  }


}
