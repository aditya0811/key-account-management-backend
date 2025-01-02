package io.aditya.kam.convertor;

import io.aditya.kam.builder.KeyAccountManagerBuilder;
import io.aditya.kam.entity.KeyAccountManagerEntity;
import io.aditya.kam.model.KeyAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class KeyAccountManagerConvertor {


  public KeyAccountManagerEntity toEntity(KeyAccountManager keyAccountManager) {
    return KeyAccountManagerEntity.builder()
        .name(keyAccountManager.getName())
        .role(keyAccountManager.getRole())
        .contactInformation(keyAccountManager.getContactInformation())
        .workingHours(keyAccountManager.getWorkingHours())
        .build();
  }

  public KeyAccountManager toModel(KeyAccountManagerEntity keyAccountManagerEntity) {
    return new KeyAccountManagerBuilder().keyAccountManagerID(keyAccountManagerEntity.getKeyAccountManagerID())
        .name(keyAccountManagerEntity.getName())
        .role(keyAccountManagerEntity.getRole())
        .contactInformation(keyAccountManagerEntity.getContactInformation())
        .workingHours(keyAccountManagerEntity.getWorkingHours())
        .build();
  }
}
