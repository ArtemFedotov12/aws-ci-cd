package com.start.springlearningdemo.config;

import com.start.springlearningdemo.security.AuthorizationProvider;
import com.start.springlearningdemo.security.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserDetailsService userDetailsService;

    private final AuthorizationProvider authorizationProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    public WebSecurityConfig(final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                             @Qualifier("userService")
                             final UserDetailsService userDetailsService,
                             final AuthorizationProvider authorizationProvider,
                             final JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.authorizationProvider = authorizationProvider;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
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
                //.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic((httpBasic) -> httpBasic.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .addFilterBefore(jwt2AuthFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    private Jwt2AuthFilter jwt2AuthFilter() throws Exception {
        Jwt2AuthFilter jwt2AuthFilter = new Jwt2AuthFilter();
        jwt2AuthFilter.setAuthenticationManager(authenticationManager());
        return jwt2AuthFilter;
    }

    @Bean
    public PasswordEncoder encoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager()
            throws Exception {
        //AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
       // authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(encoder);
        //return authenticationManagerBuilder.build();

        final ProviderManager providerManager =
                new ProviderManager(new ArrayList<>(List.of(authorizationProvider, jwtAuthenticationProvider)));
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }



}
