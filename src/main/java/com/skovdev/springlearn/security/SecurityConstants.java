package com.skovdev.springlearn.security;

public interface SecurityConstants {
    String AUTH_SECRET = "isItOkayToKeepItInCode";
    String AUTH_TOKEN_PREFIX = "Bearer ";
    String AUTH_HEADER_STRING = "Authorization";
    long AUTH_TOCKEN_EXPIRATION_TIME = 864_000_000; // 10 days
}
