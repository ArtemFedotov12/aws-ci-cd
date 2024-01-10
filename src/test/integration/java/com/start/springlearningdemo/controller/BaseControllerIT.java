package com.start.springlearningdemo.controller;

import static com.start.springlearningdemo.utils.Constants.TOKEN_PREFIX;

import com.start.springlearningdemo.config.TestControllerConfiguration;
import com.start.springlearningdemo.security.TokenProvider;
import com.start.springlearningdemo.security.config.WebSecurityConfig;
import com.start.springlearningdemo.security.provider.AuthorizationProvider;
import com.start.springlearningdemo.security.provider.JwtAuthenticationProvider;
import com.start.springlearningdemo.service.ItemService;
import com.start.springlearningdemo.service.file.FileMessageSender;
import com.start.springlearningdemo.service.file.FileProcessor;
import com.start.springlearningdemo.service.file.FileService;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
    includeFilters = {
      @ComponentScan.Filter(
          type = FilterType.ASSIGNABLE_TYPE,
          classes = {JwtAuthenticationProvider.class, WebSecurityConfig.class})
    })
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@Import({TestControllerConfiguration.class})
public abstract class BaseControllerIT {

  public static final String JWT_USER_TOKEN = "someJwtToken";
  public static final String JWT_USER_TOKEN_WITH_PREFIX = TOKEN_PREFIX + JWT_USER_TOKEN;

  @Autowired protected MockMvc mockMvc;

  @MockBean protected AuthenticationManager authenticationManager;

  @Autowired protected TokenProvider tokenProvider;

  @MockBean protected FileService fileService;

  @MockBean protected AuthorizationProvider authorizationProvider;

  @MockBean protected FileMessageSender fileMessageSender;

  @MockBean protected FileProcessor fileProcessor;

  @MockBean protected ItemService itemService;
}
