package com.ebsolutions.projects.testing.spring.cucumber.client;

import com.ebsolutions.projects.testing.spring.cucumber.config.DatabaseConfig;
import com.ebsolutions.projects.testing.spring.cucumber.model.Client;
import com.ebsolutions.projects.testing.spring.cucumber.shared.SortKeyType;
import com.ebsolutions.projects.testing.spring.cucumber.shared.exception.DataProcessingException;
import com.ebsolutions.projects.testing.spring.cucumber.shared.util.UniqueIdGenerator;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;

@Slf4j
@Repository
@AllArgsConstructor
public class ClientDao {
  private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
  private final DatabaseConfig databaseConfig;

  public List<Client> readAll() throws DataProcessingException {
    try {
      DynamoDbTable<ClientDto> clientTable = clientDtoDynamoDbTable();

      List<ClientDto> clientDtos = clientTable
          .query(
              QueryConditional.keyEqualTo(
                  Key
                      .builder()
                      .partitionValue(SortKeyType.CLIENT.name())
                      .build()
              )
          )
          .items()
          .stream()
          .toList();

      return clientDtos.stream()
          .map(clientDto ->
              Client.builder()
                  .clientId(StringUtils.remove(clientDto.getSortKey(), SortKeyType.CLIENT.name()))
                  .name(clientDto.getName())
                  .createdOn(clientDto.getCreatedOn())
                  .lastUpdatedOn(clientDto.getLastUpdatedOn())
                  .build()
          ).collect(Collectors.toList());
    } catch (Exception e) {
      log.error("ERROR::{}", this.getClass().getName(), e);
      throw new DataProcessingException(
          MessageFormat.format("Error in {0}", this.getClass().getName()), e);
    }
  }

  public List<Client> create(List<Client> clients) {
    try {
      LocalDateTime now = LocalDateTime.now();

      List<ClientDto> clientDtos = new ArrayList<>();

      // TODO LEARN Parallel and go faster
      clients.forEach(client ->
          clientDtos.add(ClientDto.builder()
              .partitionKey(SortKeyType.CLIENT.name())
              .sortKey(SortKeyType.CLIENT + UniqueIdGenerator.generate())
              .name(client.getName())
              .createdOn(now)
              .lastUpdatedOn(now)
              .build())
      );

      WriteBatch.Builder<ClientDto> writeBatchBuilder = WriteBatch.builder(ClientDto.class);

      clientDtos.forEach(writeBatchBuilder::addPutItem);

      WriteBatch writeBatch = writeBatchBuilder
          .mappedTableResource(clientDtoDynamoDbTable())
          .build();

//      BatchWriteResult batchWriteResult =
      this.dynamoDbEnhancedClient.batchWriteItem(
          BatchWriteItemEnhancedRequest
              .builder()
              .addWriteBatch(writeBatch)
              .build()
      );

//      if (!batchWriteResult.unprocessedPutItemsForTable(clientDtoDynamoDbTable()).isEmpty()) {
//        batchWriteResult.unprocessedPutItemsForTable(clientDtoDynamoDbTable()).forEach(key ->
//            log.info(key.toString()));
//      }

      return clientDtos.stream().map(clientDto ->
          Client.builder()
              .clientId(StringUtils.remove(clientDto.getSortKey(), SortKeyType.CLIENT.name()))
              .name(clientDto.getName())
              .createdOn(clientDto.getCreatedOn())
              .lastUpdatedOn(clientDto.getLastUpdatedOn())
              .build()
      ).collect(Collectors.toList());

    } catch (Exception e) {
      throw new DataProcessingException(
          MessageFormat.format("Error in {0}: {1}", this.getClass().getName(), e.getMessage()));
    }
  }

  private DynamoDbTable<ClientDto> clientDtoDynamoDbTable() {
    return dynamoDbEnhancedClient
        .table(databaseConfig.getTableName(), TableSchema.fromBean(ClientDto.class));
  }
}
