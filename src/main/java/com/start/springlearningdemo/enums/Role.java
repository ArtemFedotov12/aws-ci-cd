package com.start.springlearningdemo.enums;

import lombok.Getter;

@Getter
public enum Role {
  ADMIN("Admin", "It is admin"),
  USER("User", "It is user");

  private final String value;
  private final String description;

  Role(final String value, final String description) {
    this.value = value;
    this.description = description;
  }
}
