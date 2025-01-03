package io.aditya.kam.convertor;

import io.aditya.kam.entity.InteractionEntity;
import io.aditya.kam.model.Interaction;
import org.springframework.stereotype.Service;


@Service
public class InteractionConvertor {
  public InteractionEntity toEntity(Interaction interaction) {
    return InteractionEntity.builder()
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

  public Interaction toModel(InteractionEntity interactionEntity) {
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