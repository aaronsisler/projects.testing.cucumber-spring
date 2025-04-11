package com.ebsolutions.projects.testing.spring.cucumber.client;

import com.ebsolutions.projects.testing.spring.cucumber.model.Client;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@AllArgsConstructor
public class ClientDao {

  public List<Client> readAll() {
    try {
      log.info("In the DAO:: readAll");
      return Collections.emptyList();
    } catch (Exception e) {
      log.error("ERROR::{}", this.getClass().getName(), e);
      return Collections.emptyList();
    }
  }

  public List<Client> create(List<Client> clients) {
    try {
      log.info("In the DAO:: create");
      return Collections.emptyList();


    } catch (Exception e) {
      log.error("ERROR::{}", this.getClass().getName(), e);
      return Collections.emptyList();
    }
  }
}