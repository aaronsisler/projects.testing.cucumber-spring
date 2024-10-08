package com.ebsolutions.projects.testing.spring.cucumber.config;


import com.ebsolutions.projects.testing.spring.cucumber.Constants;
import java.net.URI;
import lombok.Getter;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DatabaseConfig {
  @Getter
  @Value("${database.tableName:`Database table name not found in environment`}")
  public String tableName;

  @Value("${database.endpoint:`Database endpoint not found in environment`}")
  protected String endpoint;

  @Bean
  @Profile({"local"})
  public DynamoDbEnhancedClient localClientInstantiation() {
    DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
        .endpointOverride(URI.create(endpoint))
        .region(Region.US_EAST_1)
        .credentialsProvider(staticCredentialsProvider())
        .build();

    return DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build();
  }

  @Bean
  @Profile({"default"})
  public DynamoDbEnhancedClient defaultDynamoDbEnhancedClient() {
    throw new NotImplementedException(Constants.PROFILE_NOT_SUPPORTED);
  }

  private StaticCredentialsProvider staticCredentialsProvider() {
    String awsAccessKeyId = "accessKeyId";
    String awsSecretAccessKey = "secretAccessKey";

    return StaticCredentialsProvider.create(
        AwsBasicCredentials.create(awsAccessKeyId, awsSecretAccessKey));
  }
}