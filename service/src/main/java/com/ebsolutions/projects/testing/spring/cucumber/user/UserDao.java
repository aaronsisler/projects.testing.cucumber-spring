package com.ebsolutions.projects.testing.spring.cucumber.user;

import com.ebsolutions.projects.testing.spring.cucumber.model.User;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@AllArgsConstructor
public class UserDao {

  public List<User> readAll() {
    try {
      log.info("In the DAO:: readAll");
      User user = User.builder().userId("user123").name("Read All Users Name!").build();

      return Collections.singletonList(user);
    } catch (Exception e) {
      log.error("ERROR::{}", this.getClass().getName(), e);
      return Collections.emptyList();
    }
  }

  public List<User> create(List<User> users) {
    try {
      log.info("In the DAO:: create");

      return users;
    } catch (Exception e) {
      log.error("ERROR::{}", this.getClass().getName(), e);
      return Collections.emptyList();
    }
  }
}