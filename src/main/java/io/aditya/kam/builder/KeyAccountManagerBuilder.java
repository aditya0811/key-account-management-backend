package io.aditya.kam.builder;

import io.aditya.kam.entity.KeyAccountManager;
public class KeyAccountManagerBuilder {
  private Integer keyAccountManagerID;
  private String name;
  private String role;
  //Should have country code
  private String contactInformation;
  private String workingHours;

  public KeyAccountManagerBuilder() {
  }

  public KeyAccountManagerBuilder keyAccountManagerID(Integer val) {
    this.keyAccountManagerID = val;
    return this;
  }

  public KeyAccountManagerBuilder name(String val) {
    this.name = val;
    return this;
  }

  public KeyAccountManagerBuilder role(String val) {
    this.role = val;
    return this;
  }

  public KeyAccountManagerBuilder contactInformation(String val) {
    this.contactInformation = val;
    return this;
  }

  public KeyAccountManagerBuilder workingHours(String val) {
    this.workingHours = val;
    return this;
  }

  public KeyAccountManager build() {
    return new KeyAccountManager(keyAccountManagerID, name, role, contactInformation, workingHours);
  }
}
