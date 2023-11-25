package com.start.springlearningdemo.security.provider;

import com.start.springlearningdemo.exception.UnauthorizedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    public AuthorizationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String username = authentication.getName();
        final Object credentials = authentication.getCredentials();
        if (username == null || credentials == null) {
            throw new UnauthorizedException();
        }
        final String password = credentials.toString();
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // Example: validating credentials
        if (userDetails == null || !password.equals(userDetails.getPassword())) {
            throw new UnauthorizedException();
        }
        // Create a fully authenticated Authentication object
        return new UsernamePasswordAuthenticationToken(
                userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // Return true if this AuthenticationProvider supports the provided authentication class
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
