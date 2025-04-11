package com.ebsolutions.projects.testing.spring.cucumber.user;

import com.ebsolutions.projects.testing.spring.cucumber.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserController {
  private final UserService userService;


  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> post(@RequestBody @NotEmpty List<@Valid User> users) {
    try {
      return ResponseEntity.ok(userService.create(users));
    } catch (Exception exception) {
      return ResponseEntity.internalServerError().body(exception.getMessage());
    }
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getAll() {
    try {
      List<User> users = userService.readAll();

      return !users.isEmpty() ? ResponseEntity.ok(users) : ResponseEntity.noContent().build();
    } catch (Exception exception) {
      return ResponseEntity.internalServerError().body(exception.getMessage());
    }
  }
}
