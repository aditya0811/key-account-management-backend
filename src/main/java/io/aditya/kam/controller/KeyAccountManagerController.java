package io.aditya.kam.controller;

import io.aditya.kam.entity.KeyAccountManager;
import io.aditya.kam.service.KeyAccountManagerService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller helps to expose REST endpoints
 */
@RestController
public class KeyAccountManagerController {

  private final KeyAccountManagerService keyAccountManagerService;

  @Autowired
  public KeyAccountManagerController(KeyAccountManagerService keyAccountManagerService) {
    this.keyAccountManagerService = keyAccountManagerService;
  }

  @PutMapping(path="/v1/key-account-managers/{keyAccountManagerID}")
  public ResponseEntity<KeyAccountManager> createKeyAccountManager(
      @PathVariable Integer keyAccountManagerID,
      @RequestBody final KeyAccountManager keyAccountManager) {
    keyAccountManager.setKeyAccountManagerID(keyAccountManagerID);
    final KeyAccountManager savedKeyAccountManager = keyAccountManagerService.create(keyAccountManager);
    ResponseEntity<KeyAccountManager> response =
        new ResponseEntity<>(savedKeyAccountManager, HttpStatus.CREATED);
    return response;
  }

  @GetMapping(path="/v1/key-account-managers/{keyAccountManagerID}")
  public ResponseEntity<KeyAccountManager> retrieveKeyAccountManager(@PathVariable Integer keyAccountManagerID) {
    final Optional<KeyAccountManager> foundKeyAccountManager = keyAccountManagerService.findById(keyAccountManagerID);
    return foundKeyAccountManager
        .map(keyAccountManager -> new ResponseEntity<>(keyAccountManager, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping(path = "/v1/key-account-managers")
  public ResponseEntity<List<KeyAccountManager>> listKeyAccountManagers() {
    return new ResponseEntity<List<KeyAccountManager>>(keyAccountManagerService.listKeyAccountManagers(), HttpStatus.OK);
  }


}
