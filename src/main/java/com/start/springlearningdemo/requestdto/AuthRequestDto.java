package com.start.springlearningdemo.requestdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthRequestDto {
    private String username;
    private String password;
}
