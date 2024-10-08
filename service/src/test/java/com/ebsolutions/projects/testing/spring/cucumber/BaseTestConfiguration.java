package com.ebsolutions.projects.testing.spring.cucumber;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

@TestConfiguration
public class BaseTestConfiguration {
  @MockBean
  private DynamoDbEnhancedClient dynamoDbEnhancedClient;
}
