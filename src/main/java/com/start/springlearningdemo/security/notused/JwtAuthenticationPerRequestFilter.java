package com.start.springlearningdemo.security.notused;

import com.start.springlearningdemo.security.TokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.start.springlearningdemo.utils.Constants.HEADER_STRING;
import static com.start.springlearningdemo.utils.Constants.TOKEN_PREFIX;

// this class we don't use, instead see JwtAuthenticationProvider.class
@Slf4j
//@Component
public class JwtAuthenticationPerRequestFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    public JwtAuthenticationPerRequestFilter(final TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        final String header = req.getHeader(HEADER_STRING);
        String username = null;
        String authToken = null;
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX, "");
            try {
                username = tokenProvider.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                log.error("An error occurred during getting username from token", e);
            } catch (ExpiredJwtException e) {
                log.warn("The token is expired", e);
            } catch (SignatureException e) {
                log.error("Verifying of an existing signature of a JWT failed.", e);
            }
        } else {
            log.warn("Couldn't find '{}' string, will ignore the header", TOKEN_PREFIX);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // do not fire request to DB
            final UserDetails userDetails = tokenProvider.getUserDetailsFromToken(authToken);

            if (tokenProvider.validateToken(authToken)) {
                final UsernamePasswordAuthenticationToken authentication = tokenProvider.getAuthentication(authToken, SecurityContextHolder.getContext().getAuthentication(), userDetails);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                log.info("Authenticated user '{}'. Set authentication to the security context", username);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(req, res);
    }
}