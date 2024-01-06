package com.start.springlearningdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  // this controller needs user authentication via jwt
  @GetMapping("/test")
  public String test() {
    return "test";
  }
}
