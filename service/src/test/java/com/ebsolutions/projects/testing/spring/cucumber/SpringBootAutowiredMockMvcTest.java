package com.ebsolutions.projects.testing.spring.cucumber;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


public class SpringBootAutowiredMockMvcTest extends BaseTestContext {
  @Autowired
  private MockMvc mockMvc;

  @Test
  void whenHealthCheckIsCalledShouldReturnCorrectMessage() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.get(Constants.HEALTH_CHECK_URL))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("UP")));
  }

  @Test
  void whenInfoCheckIsCalledShouldReturnCorrectMessage() throws Exception {
    this.mockMvc.perform(get(Constants.INFO_CHECK_URL))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.build.group", is("com.ebsolutions.projects.testing.spring.cucumber")))
        .andExpect(jsonPath("$.build.artifact", is("spring-cucumber-service")))
        .andExpect(jsonPath("$.build.name", is("Spring Cucumber Service")))
        .andExpect(jsonPath("$.build.version", notNullValue()))
        .andExpect(jsonPath("$.build.time", notNullValue()));
  }
}
