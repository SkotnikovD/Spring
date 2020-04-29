package com.skovdev.springlearn.security;

public interface SecurityConstants {
    String AUTH_SECRET = "isItOkayToKeepItInCode";
    String AUTH_TOKEN_PREFIX = "Bearer ";
    String AUTH_HEADER_STRING = "Authorization";
    long AUTH_TOCKEN_EXPIRATION_TIME_MS = 8_640_000_000L; // 100 days
}
