package io.aditya.kam.service;

import io.aditya.kam.model.KeyAccountManager;
import java.util.List;
import java.util.Optional;


public interface KeyAccountManagerService {
  KeyAccountManager create(KeyAccountManager keyAccountManager);
  Optional<KeyAccountManager> findById(Integer id);
  List<KeyAccountManager> listKeyAccountManagers();
}
