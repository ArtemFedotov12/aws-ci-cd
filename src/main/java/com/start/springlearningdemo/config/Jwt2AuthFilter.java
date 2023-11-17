package com.start.springlearningdemo.config;

import com.start.springlearningdemo.security.JwtAuthorizationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

public class Jwt2AuthFilter extends AbstractAuthenticationProcessingFilter {
    public Jwt2AuthFilter() {
        super(new AndRequestMatcher(
                (new NegatedRequestMatcher(new OrRequestMatcher(
                        new AntPathRequestMatcher("/login"),
                        new AntPathRequestMatcher("/health")))),
                new AntPathRequestMatcher("/**")));
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        if (!requiresAuthentication(request, response)) {
            chain.doFilter(request, response);
            return;
        }
        final Authentication authenticationToken = attemptAuthentication(request, response);
        final Authentication authenticated =
                getAuthenticationManager().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticated);
        chain.doFilter(request, response);
        //System.out.println();
        //super.doFilter(request, response, chain);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        final String token = request.getHeader("Authorization");
        return new JwtAuthorizationToken(token);
    }

}
