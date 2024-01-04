package com.start.springlearningdemo;

import com.start.springlearningdemo.security.TokenProvider;
import com.start.springlearningdemo.security.config.WebSecurityConfig;
import com.start.springlearningdemo.security.filter.JwtAuthenticationProcessingFilter;
import com.start.springlearningdemo.security.provider.AuthorizationProvider;
import com.start.springlearningdemo.security.provider.JwtAuthenticationProvider;
import com.start.springlearningdemo.service.user.UserServiceImpl;
import jakarta.annotation.PostConstruct;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


@SpringBootTest(classes = {TestConfiguration.class})
//@WebMvcTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

//@WebMvcTest
//@ContextConfiguration(classes={SpringLearningDemoApplication.class, WebSecurityConfig.class})
@ActiveProfiles("test")
@Import({/*WebSecurityConfig.class, AuthorizationProvider.class, TokenProvider.class, JwtAuthenticationProvider.class,*/ TestConfiguration.class})
public class BaseServiceIT {

    //@Autowired
    protected MockMvc mockMvc;

    @Autowired protected WebApplicationContext wac;

    @Autowired protected FilterChainProxy springSecurityFilterChain;

    @MockBean
    protected UserDetailsService userDetailsService;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                //.addFilter(springSecurityFilterChain)
                .apply(springSecurity())
                .build();
    }


}
