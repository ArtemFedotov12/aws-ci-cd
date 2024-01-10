package com.start.springlearningdemo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class HealthControllerIT extends BaseControllerIT {

  @Test
  void whenCheckHealthThenSuccess() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/health")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
}
