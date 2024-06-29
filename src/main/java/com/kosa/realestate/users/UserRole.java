package com.kosa.realestate.users;

import lombok.Getter;

@Getter
public enum UserRole {
  ADMIN("ADMIN"),
  USER("USER"),
  AGENT("AGENT");

  UserRole(String value) {
    this.value = value;
  }

  private final String value;
}
