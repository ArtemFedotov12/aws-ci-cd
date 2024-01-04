package com.start.springlearningdemo.security;

import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.start.springlearningdemo.utils.Constants.*;

@Component
public class TokenProvider implements Serializable {

    @Value("${jwt.signing.key}")
    private String jwtSigningKey;


    public String getUsernameFromToken(final String token) {
        //Function Interface --- getString("sub");
        return getClaimFromToken(token, Claims::getSubject);
    }

    public List<String> getRolesFromToken(String token) {
        return List.of(
                getClaimFromToken(token, claims -> claims.get(AUTHORITIES_KEY)).toString()
                        .split(ROLES_DELIMITER));
    }

    public UserDetails getUserDetailsFromToken(final String token) {
        final String username = getUsernameFromToken(token);
        final List<String> roles = getRolesFromToken(token);
        return new org.springframework.security.core.userdetails.User(
                username,
                StringUtils.EMPTY,
                roles.stream().map(SimpleGrantedAuthority::new).toList());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        // claims.getString("sub");
        // claims.get(Claims.EXPIRATION, Date.class);
        return claimsResolver.apply(claims);
    }

    //Evaluate: {sub=user2, scopes=ROLE_USER, iat=1584700835, exp=1584718835}
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSigningKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(Authentication authentication) {
        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return Jwts.builder()
                // key - "sub"
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS256, jwtSigningKey)
                // key - 'iat'
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //1000 - means milliseconds  and 5*60*60 = 18000 seconds = 5 hours
                // key - 'exp'
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .compact();
    }

    public Boolean validateToken(final String token) {
        return !isTokenExpired(token);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(final String token, final UserDetails userDetails) {

        final JwtParser jwtParser = Jwts.parser().setSigningKey(jwtSigningKey);
        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
        final Claims claims = claimsJws.getBody();

        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(ROLES_DELIMITER))
                        .map(SimpleGrantedAuthority::new)
                        .toList();

        return new UsernamePasswordAuthenticationToken(userDetails, StringUtils.EMPTY, authorities);
    }

}
