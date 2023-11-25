package com.start.springlearningdemo.security.provider;

import com.start.springlearningdemo.exception.UnauthorizedException;
import com.start.springlearningdemo.security.TokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final TokenProvider tokenProvider;

    public JwtAuthenticationProvider(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String token = authentication.getCredentials().toString();
        String username = null;
        try {
            username = tokenProvider.getUsernameFromToken(token);
        } catch (final ExpiredJwtException e) {
            log.warn("The token is expired", e);
        } catch (final SignatureException e) {
            log.error("Verifying of an existing signature of a JWT failed.", e);
        } catch (final Exception e) {
            log.error("Error occurred while extracting data from token", e);
        }
        if (StringUtils.isBlank(username)) {
            log.error("An error occurred during getting username from token");
            throw new UnauthorizedException();
        }

        UsernamePasswordAuthenticationToken tokenProviderAuthentication = null;
        if (SecurityContextHolder.getContext().getAuthentication() == null) {

            // do not fire request to DB
            final UserDetails userDetails = tokenProvider.getUserDetailsFromToken(token);

            if (tokenProvider.validateToken(token)) {
                tokenProviderAuthentication = tokenProvider.getAuthentication(
                        token,
                        SecurityContextHolder.getContext().getAuthentication(),
                        userDetails);
                log.info("Authenticated user '{}'. Set authentication to the security context", username);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                log.error("Token validation failed");
                throw new UnauthorizedException();
            }
        }

        return tokenProviderAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtAuthorizationToken.class);
    }

}
