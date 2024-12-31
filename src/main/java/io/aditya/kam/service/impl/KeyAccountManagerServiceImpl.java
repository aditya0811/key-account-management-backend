package io.aditya.kam.service.impl;

import io.aditya.kam.entity.KeyAccountManager;
import io.aditya.kam.entity.KeyAccountManagerEntity;
import io.aditya.kam.repository.KeyAccountManagerRepository;
import io.aditya.kam.service.KeyAccountManagerService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class KeyAccountManagerServiceImpl implements KeyAccountManagerService {

  private final KeyAccountManagerRepository keyAccountManagerRepository;

  /**
   * Using a constructor based injection, as the variable is final, if we use setter based injection
   * the repository variable is not final
   * @param keyAccountManagerRepository
   */
  @Autowired
  public KeyAccountManagerServiceImpl(final KeyAccountManagerRepository keyAccountManagerRepository) {
    this.keyAccountManagerRepository = keyAccountManagerRepository;

  }

  @Override
  public KeyAccountManager create(KeyAccountManager keyAccountManager) {
    KeyAccountManagerEntity keyAccountManagerEntity =
        keyAccountManagerRepository.save(keyAccountManagerToEntityConversion(keyAccountManager));
    return entityToKeyAccountManagerConversion(keyAccountManagerEntity);
  }

  @Override
  public Optional<KeyAccountManager> findById(Integer id) {
    Optional<KeyAccountManagerEntity> foundKeyAccountManagerEntity
        = keyAccountManagerRepository.findById(String.valueOf(id));

    return foundKeyAccountManagerEntity.map(this::entityToKeyAccountManagerConversion);
  }

  @Override
  public List<KeyAccountManager> listKeyAccountManagers() {
    final List<KeyAccountManagerEntity> foundKeyAccountManagers = keyAccountManagerRepository.findAll();
    return foundKeyAccountManagers.stream()
        .map(this::entityToKeyAccountManagerConversion)
        .collect(Collectors.toList());
  }

  private KeyAccountManagerEntity keyAccountManagerToEntityConversion(KeyAccountManager keyAccountManager) {
    return KeyAccountManagerEntity.builder().keyAccountManagerID(keyAccountManager.getKeyAccountManagerID())
        .name(keyAccountManager.getName())
        .role(keyAccountManager.getRole())
        .contactInformation(keyAccountManager.getContactInformation())
        .workingHoursInUTC(keyAccountManager.getWorkingHoursInUTC())
        .build();
  }

  private KeyAccountManager entityToKeyAccountManagerConversion(KeyAccountManagerEntity keyAccountManagerEntity) {
    return KeyAccountManager.builder().keyAccountManagerID(keyAccountManagerEntity.getKeyAccountManagerID())
        .name(keyAccountManagerEntity.getName())
        .role(keyAccountManagerEntity.getRole())
        .contactInformation(keyAccountManagerEntity.getContactInformation())
        .workingHoursInUTC(keyAccountManagerEntity.getWorkingHoursInUTC())
        .build();
  }
}
