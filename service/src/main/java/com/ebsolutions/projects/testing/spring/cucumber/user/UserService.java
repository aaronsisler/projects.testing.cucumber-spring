package com.ebsolutions.projects.testing.spring.cucumber.user;

import com.ebsolutions.projects.testing.spring.cucumber.model.User;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserService {
  private final UserDao userDao;

  public List<User> create(List<User> users) {
    return userDao.create(users);
  }

  public List<User> readAll() {
    return userDao.readAll();
  }
}
