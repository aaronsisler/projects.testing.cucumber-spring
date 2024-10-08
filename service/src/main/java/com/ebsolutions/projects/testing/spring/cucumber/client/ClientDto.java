package com.ebsolutions.projects.testing.spring.cucumber.client;

import com.ebsolutions.projects.testing.spring.cucumber.shared.DatabaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@Data
@Slf4j
@DynamoDbBean
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ClientDto extends DatabaseDto {
}
