package com.start.springlearningdemo.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthorizationToken extends AbstractAuthenticationToken {
    private final String token;

    /**
     * Creates a token.
     *
     * @param token the jwt token value.
     */
    public JwtAuthorizationToken(final String token) {
        super(null);
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
