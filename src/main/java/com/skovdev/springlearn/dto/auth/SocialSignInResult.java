package com.skovdev.springlearn.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * Result of performing signin with third-party accounts
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class SocialSignInResult {

    /**
     * Authorization Bearer token
     */
    @NotNull
    String Jwt;

    /**
     * True if signin results to creation of new user
     */
    boolean isNewUser;

}
