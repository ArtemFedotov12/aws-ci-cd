package com.start.springlearningdemo.security.provider;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        JwtAuthorizationToken that = (JwtAuthorizationToken) o;
        return Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), token);
    }
}
