package com.start.springlearningdemo.controller;

import static com.start.springlearningdemo.constants.ApplicationConstants.AUTHORIZATION_HEADER;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class UserControllerIT extends BaseControllerIT {

  @Test
  void whenCheckEndpointWithAuthorizationThenSuccess() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/test")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER, JWT_USER_TOKEN_WITH_PREFIX))
        .andExpect(status().isOk());
  }

  @Test
  void whenCheckEndpointWithAuthorizationButJwtWasNotSendThenFailed401() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/test")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }
}
