package com.ebsolutions.projects.testing.spring.cucumber;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class EmptySpringBootTest extends BaseTestContext {

  @Autowired
  private SpringCucumberServiceApplication application;

  @Test
  void contextLoads() {
    assertThat(application).isNotNull();
  }
}
