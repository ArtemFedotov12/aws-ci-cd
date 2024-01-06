package com.start.springlearningdemo.security.filter;

import static com.start.springlearningdemo.utils.Constants.HEADER_STRING;
import static com.start.springlearningdemo.utils.Constants.TOKEN_PREFIX;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.start.springlearningdemo.responsedto.ApplicationExceptionResponseDto;
import com.start.springlearningdemo.security.provider.JwtAuthenticationProvider;
import com.start.springlearningdemo.security.provider.JwtAuthorizationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.*;

public class JwtAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

  private final JwtAuthenticationProvider jwtAuthenticationProvider;

  public JwtAuthenticationProcessingFilter(
      final RequestMatcher requiresAuthenticationRequestMatcher,
      final JwtAuthenticationProvider jwtAuthenticationProvider) {
    super(requiresAuthenticationRequestMatcher);
    this.jwtAuthenticationProvider = jwtAuthenticationProvider;
  }

  @Override
  public Authentication attemptAuthentication(
      final HttpServletRequest request, final HttpServletResponse response) {
    final String token = extractTokenFromRequest(request);
    final JwtAuthorizationToken jwtAuthorizationToken = new JwtAuthorizationToken(token);
    try {
      return jwtAuthenticationProvider.authenticate(jwtAuthorizationToken);
    } catch (final Exception e) {
      throw new AuthenticationServiceException("Authentication error", e);
    }
  }

  private String extractTokenFromRequest(final HttpServletRequest request) {
    final String header = request.getHeader(HEADER_STRING);
    final String authToken;
    if (header != null && header.startsWith(TOKEN_PREFIX)) {
      authToken = header.replace(TOKEN_PREFIX, StringUtils.EMPTY);
    } else {
      authToken = null;
    }
    return authToken;
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult)
      throws IOException, ServletException {
    SecurityContextHolder.getContext().setAuthentication(authResult);
    chain.doFilter(request, response);
  }

  @Override
  protected void unsuccessfulAuthentication(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
      throws IOException, ServletException {
    // Customize the response structure
    final ApplicationExceptionResponseDto responseBody =
        ApplicationExceptionResponseDto.builder()
            .message(HttpStatus.UNAUTHORIZED.getReasonPhrase())
            .status(401)
            .errors(Map.of())
            .build();

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    new ObjectMapper().writeValue(response.getWriter(), responseBody);
  }
}
