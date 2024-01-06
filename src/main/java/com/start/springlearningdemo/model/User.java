package com.start.springlearningdemo.model;

import com.start.springlearningdemo.enums.Role;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
  private String username;
  private String password;
  private Set<Role> roles;
}
