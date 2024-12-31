package io.aditya.kam.service.impl;

import io.aditya.kam.entity.Interaction;
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

  /**
   * Using a constructor based injection, as the variable is final, if we use setter based injection
   * the repository variable is not final
   * @param interactionRepository
   */
  @Autowired
  public InteractionServiceImpl(final InteractionRepository interactionRepository) {
    this.interactionRepository = interactionRepository;

  }

  @Override
  public Interaction create(Interaction interaction) {
    InteractionEntity interactionEntity =
        interactionRepository.save(interactionToEntityConversion(interaction));
    return entityToInteractionConversion(interactionEntity);
  }

  @Override
  public Optional<Interaction> findById(String id) {
    Optional<InteractionEntity> foundInteractionEntity
        = interactionRepository.findById(String.valueOf(id));

    return foundInteractionEntity.map(this::entityToInteractionConversion);
  }

  @Override
  public List<Interaction> listInteractions() {
    final List<InteractionEntity> foundInteractions = interactionRepository.findAll();
    return foundInteractions.stream()
        .map(this::entityToInteractionConversion)
        .collect(Collectors.toList());
  }

  private InteractionEntity interactionToEntityConversion(Interaction interaction) {
    return InteractionEntity.builder().interactionID(interaction.getInteractionID())
        .keyAccountManagerID(interaction.getKeyAccountManagerID())
        .customerID(interaction.getCustomerID())
        .pointOfContactID(interaction.getPointOfContactID())
        .isConverted(interaction.getIsConverted())
        .interactionTimestamp(interaction.getInteractionTimestamp())
        .audioLink(interaction.getAudioLink())
        .orderID(interaction.getOrderID())
        .interactionComment(interaction.getInteractionComment())
        .isKeyAccountManagerChanged(interaction.getIsKeyAccountManagerChanged())
        .changedKeyAccountManagerID(interaction.getChangedKeyAccountManagerID())
        .isPointOfContactChanged(interaction.getIsPointOfContactChanged())
        .changedPointOfContactID(interaction.getChangedPointOfContactID())
        .isOrderPlaced(interaction.getIsOrderPlaced())
        .transactionValue(interaction.getTransactionValue())
        .build();
  }

  private Interaction entityToInteractionConversion(InteractionEntity interactionEntity) {
    return Interaction.builder().interactionID(interactionEntity.getInteractionID())
        .keyAccountManagerID(interactionEntity.getKeyAccountManagerID())
        .customerID(interactionEntity.getCustomerID())
        .pointOfContactID(interactionEntity.getPointOfContactID())
        .isConverted(interactionEntity.getIsConverted())
        .interactionTimestamp(interactionEntity.getInteractionTimestamp())
        .audioLink(interactionEntity.getAudioLink())
        .orderID(interactionEntity.getOrderID())
        .interactionComment(interactionEntity.getInteractionComment())
        .isKeyAccountManagerChanged(interactionEntity.getIsKeyAccountManagerChanged())
        .changedKeyAccountManagerID(interactionEntity.getChangedKeyAccountManagerID())
        .isPointOfContactChanged(interactionEntity.getIsPointOfContactChanged())
        .changedPointOfContactID(interactionEntity.getChangedPointOfContactID())
        .isOrderPlaced(interactionEntity.getIsOrderPlaced())
        .transactionValue(interactionEntity.getTransactionValue())
        .build();
  }
}
