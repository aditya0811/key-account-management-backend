package io.aditya.kam.service.impl;

import io.aditya.kam.builder.KeyAccountManagerBuilder;
import io.aditya.kam.convertor.KeyAccountManagerConvertor;
import io.aditya.kam.model.KeyAccountManager;
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


  private final KeyAccountManagerConvertor keyAccountManagerConvertor;

  /**
   * Using a constructor based injection
   * @param keyAccountManagerRepository
   */
  @Autowired
  public KeyAccountManagerServiceImpl(final KeyAccountManagerRepository keyAccountManagerRepository,
      final  KeyAccountManagerConvertor keyAccountManagerConvertor) {
    this.keyAccountManagerRepository = keyAccountManagerRepository;
    this.keyAccountManagerConvertor = keyAccountManagerConvertor;

  }

  @Override
  public KeyAccountManager create(KeyAccountManager keyAccountManager) {
    KeyAccountManagerEntity keyAccountManagerEntity =
        keyAccountManagerRepository.save(keyAccountManagerConvertor.toEntity(keyAccountManager));
    return keyAccountManagerConvertor.toModel(keyAccountManagerEntity);
  }

  @Override
  public Optional<KeyAccountManager> findById(Integer id) {
    Optional<KeyAccountManagerEntity> foundKeyAccountManagerEntity
        = keyAccountManagerRepository.findById(id);

    return foundKeyAccountManagerEntity.map(keyAccountManagerEntity
        -> keyAccountManagerConvertor.toModel(keyAccountManagerEntity));
  }

  @Override
  public List<KeyAccountManager> listKeyAccountManagers() {
    final List<KeyAccountManagerEntity> foundKeyAccountManagers = keyAccountManagerRepository.findAll();
    return foundKeyAccountManagers.stream()
        .map(keyAccountManagerEntity
            -> keyAccountManagerConvertor.toModel(keyAccountManagerEntity))
        .collect(Collectors.toList());
  }

}
