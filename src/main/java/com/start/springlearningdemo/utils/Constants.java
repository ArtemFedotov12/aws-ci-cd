package com.start.springlearningdemo.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5 * 60 * 60;

    // should be in the env variables
    public static final String SIGNING_KEY = "some_secret_key";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTHORITIES_KEY = "scopes";

    public static final String ROLES_DELIMITER = ",";
}
