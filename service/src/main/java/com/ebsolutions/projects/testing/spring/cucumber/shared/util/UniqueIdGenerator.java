package com.ebsolutions.projects.testing.spring.cucumber.shared.util;


import java.util.UUID;

public class UniqueIdGenerator {
  public static String generate() {
    return UUID.randomUUID().toString();
  }
}