package com.start.springlearningdemo.requestdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class AuthRequestDto {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
