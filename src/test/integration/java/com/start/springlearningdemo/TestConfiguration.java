package com.start.springlearningdemo;

import static com.start.springlearningdemo.BaseControllerIT.JWT_USER_TOKEN;
import static org.mockito.Mockito.when;

import com.start.springlearningdemo.enums.Role;
import com.start.springlearningdemo.security.TokenProvider;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Profile("test")
@Configuration
public class TestConfiguration {

  @Bean
  public TokenProvider tokenProvider() {
    final String userName = "someUserName";
    final TokenProvider mockTokenProvider = Mockito.mock(TokenProvider.class);
    when(mockTokenProvider.getUsernameFromToken(JWT_USER_TOKEN)).thenReturn("someUserName");

    final User mockUser =
        new User(
            userName,
            StringUtils.EMPTY,
            Stream.of(Role.USER.getValue()).map(SimpleGrantedAuthority::new).toList());
    when(mockTokenProvider.getUserDetailsFromToken(JWT_USER_TOKEN)).thenReturn(mockUser);

    when(mockTokenProvider.validateToken(JWT_USER_TOKEN)).thenReturn(true);

    UsernamePasswordAuthenticationToken mockUsernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(
            mockUser,
            StringUtils.EMPTY,
            Stream.of(Role.USER)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getValue()))
                .toList());
    when(mockTokenProvider.getAuthentication(JWT_USER_TOKEN, mockUser))
        .thenReturn(mockUsernamePasswordAuthenticationToken);
    return mockTokenProvider;
  }
}
