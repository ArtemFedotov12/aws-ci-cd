package com.start.springlearningdemo.controller;

import com.start.springlearningdemo.config.TokenProvider;
import com.start.springlearningdemo.requestdto.AuthRequestDto;
import com.start.springlearningdemo.responsedto.AuthTokenResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {


    private final AuthenticationManager authenticationManager;

    private final TokenProvider jwtTokenUtil;

    public AuthenticationController(final AuthenticationManager authenticationManager,
                                    final TokenProvider jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto authRequestDto) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDto.getUsername(),
                        authRequestDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthTokenResponseDto(token));
    }
}
