package com.start.springlearningdemo.requestdto;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthRequestDto {
  @NotEmpty private String username;
  @NotEmpty private String password;
}
