package com.start.springlearningdemo.security.config;

import com.start.springlearningdemo.security.provider.AuthorizationProvider;
import com.start.springlearningdemo.security.filter.JwtAuthenticationProcessingFilter;
import com.start.springlearningdemo.security.provider.JwtAuthenticationProvider;
import com.start.springlearningdemo.security.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.*;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;
    private final AuthorizationProvider authorizationProvider;


    public WebSecurityConfig(final TokenProvider tokenProvider,
                             final AuthorizationProvider authorizationProvider) {
        this.tokenProvider = tokenProvider;
        this.authorizationProvider = authorizationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors((cors) -> {})
                .authorizeHttpRequests(
                        (authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers(HttpMethod.GET, "/health").permitAll()
                                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                                .anyRequest()
                                .authenticated()

                )
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(getJwtAuthProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    private JwtAuthenticationProcessingFilter getJwtAuthProcessingFilter() {
        JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter = new JwtAuthenticationProcessingFilter(
                getJwtRequestMatcher(),
                getJwtAuthenticationProvider());
        jwtAuthenticationProcessingFilter.setAuthenticationManager(authenticationManager());
        return jwtAuthenticationProcessingFilter;
    }

    private RequestMatcher getJwtRequestMatcher() {
        return new AndRequestMatcher(
                (new NegatedRequestMatcher(new OrRequestMatcher(
                        new AntPathRequestMatcher("/login"),
                        new AntPathRequestMatcher("/health")))),
                new AntPathRequestMatcher("/**"));
    }

    private JwtAuthenticationProvider getJwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(tokenProvider);
    }

    // used, because we don't have db
    @Bean
    public PasswordEncoder encoder() {
        return NoOpPasswordEncoder.getInstance();
    }


    @Bean
    public AuthenticationManager authenticationManager() {
        final ProviderManager providerManager =
                new ProviderManager(new ArrayList<>(List.of(authorizationProvider)));
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }

}
