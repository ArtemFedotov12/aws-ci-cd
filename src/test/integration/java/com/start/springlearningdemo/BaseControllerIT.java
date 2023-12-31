package com.start.springlearningdemo;

import static com.start.springlearningdemo.utils.Constants.TOKEN_PREFIX;

import com.start.springlearningdemo.security.TokenProvider;
import com.start.springlearningdemo.security.config.WebSecurityConfig;
import com.start.springlearningdemo.security.provider.AuthorizationProvider;
import com.start.springlearningdemo.security.provider.JwtAuthenticationProvider;
import com.start.springlearningdemo.service.FileMessageSender;
import com.start.springlearningdemo.service.FileService;
import jakarta.servlet.Filter;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(
    includeFilters = {
      @ComponentScan.Filter(
          type = FilterType.ASSIGNABLE_TYPE,
          classes = {
            AuthorizationProvider.class,
            JwtAuthenticationProvider.class,
            WebSecurityConfig.class
          })
    })
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// @ContextConfiguration(classes={SpringLearningDemoApplication.class, WebSecurityConfig.class})
@ActiveProfiles("test")
@Import({TestConfiguration.class})
public abstract class BaseControllerIT {

  public static final String JWT_USER_TOKEN = "someJwtToken";
  public static final String JWT_USER_TOKEN_WITH_PREFIX = TOKEN_PREFIX + JWT_USER_TOKEN;

  @Autowired protected MockMvc mockMvc;

  @Autowired protected WebApplicationContext wac;

  @Autowired protected FilterChainProxy springSecurityFilterChain;

  @Autowired
  @Qualifier("springSecurityFilterChain")
  Filter filter;

  @MockBean protected AuthenticationManager authenticationManager;

  @Autowired protected TokenProvider tokenProvider;

  @MockBean protected FileService fileService;

  @MockBean protected AuthorizationProvider authorizationProvider;

  @MockBean protected FileMessageSender fileMessageSender;
}
