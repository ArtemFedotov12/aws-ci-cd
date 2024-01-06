package com.start.springlearningdemo.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

  public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5L * 60 * 60;

  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
  public static final String AUTHORITIES_KEY = "scopes";
  public static final String ROLES_DELIMITER = ",";
}
