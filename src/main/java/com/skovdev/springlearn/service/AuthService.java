package com.skovdev.springlearn.service;

import com.skovdev.springlearn.dto.auth.SocialSignInResult;
import com.skovdev.springlearn.dto.mapper.UserMapper;
import com.skovdev.springlearn.dto.user.GetFullUserDto;
import com.skovdev.springlearn.dto.user.SignUpUserDto;
import com.skovdev.springlearn.model.google.GoogleUser;
import com.skovdev.springlearn.security.JwtService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final GoogleSigninService googleSigninService;
    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public AuthService(GoogleSigninService googleSigninService, UserService userService, JwtService jwtService) {
        this.googleSigninService = googleSigninService;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * Performs Google Signin. If the user with such email exists, then perform signin. Otherwise, create user first, utilising Google user email, name and picture.
     *
     * @param idToken - ID Token, received from Google.
     * @return SocialSignInResult
     */
    public SocialSignInResult signinWithGoogle(String idToken) {
        GoogleUser googleUser = googleSigninService.verifyTokenAndExtractUserInfo(idToken);
        Optional<GetFullUserDto> user = userService.getUser(googleUser.getEmail());
        if (!user.isPresent()) {
            //XXX: Using randomly generated password for new users created via Google signin
            String random = RandomStringUtils.random(50, true, true);
            SignUpUserDto newUser = UserMapper.toDto(googleUser, random);
            GetFullUserDto createdUser = userService.registerNewUser(newUser);
            String jwtToken = jwtService.createJwtToken(newUser.getLogin(), createdUser.getRoles());
            return new SocialSignInResult(jwtToken, true);
        } else {
            String jwtToken = jwtService.createJwtToken(googleUser.getEmail(), user.get().getRoles());
            return new SocialSignInResult(jwtToken, true);
        }

    }

}
