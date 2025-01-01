package io.aditya.kam;

import io.aditya.kam.builder.KeyAccountManagerBuilder;
import io.aditya.kam.entity.KeyAccountManager;
import io.aditya.kam.entity.KeyAccountManagerEntity;


public class TestData {
  private TestData() {

  }

  public static KeyAccountManager getKeyAccountManager() {
    return new KeyAccountManagerBuilder()
        .name("Ramesh")
        .role("key-account-manager")
        .contactInformation("+91 9434434343")
        .workingHours("02:30 - 10:30")
        .build();
  }

  public static KeyAccountManagerEntity getKeyAccountManagerEntity() {
    return KeyAccountManagerEntity
        .builder()
        .name("Ramesh")
        .role("key-account-manager")
        .contactInformation("+91 9434434343")
        .workingHours("02:30 - 10:30")
        .build();
  }

}
