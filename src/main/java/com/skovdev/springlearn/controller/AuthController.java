package com.skovdev.springlearn.controller;

import com.skovdev.springlearn.dto.auth.SocialSignInResult;
import com.skovdev.springlearn.service.AuthService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.skovdev.springlearn.security.SecurityConstants.AUTH_HEADER_STRING;
import static com.skovdev.springlearn.security.SecurityConstants.AUTH_TOKEN_PREFIX;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {this.authService = authService;}

    //TODO move here methods for login/password authentication

    @ApiOperation(value = "Performs Google Signin", notes = "Receive Google ID Token. Perform signin and return JWT token (200 response). If the user with such login does't exist, then create user first, utilising Google user email, name and picture (201 response)", consumes = "text/plain", response = Void.class)
    @ResponseHeader(name = AUTH_HEADER_STRING, response = String.class, description = "Valid JWT token for user authorisation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 201, message = "Created", response = Void.class)})
    @PutMapping("/google/tokensignin")
    @SuppressWarnings("rawtypes")
    public ResponseEntity createUserOrAuthenticate(@RequestBody String idToken) {
        SocialSignInResult socialSignInResult = authService.signinWithGoogle(idToken);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AUTH_HEADER_STRING, AUTH_TOKEN_PREFIX + socialSignInResult.getJwt());
        return new ResponseEntity(null, httpHeaders, socialSignInResult.isNewUser() ? HttpStatus.CREATED : HttpStatus.OK);
    }

}
