package com.skovdev.springlearn.service;

import com.skovdev.springlearn.dto.auth.SocialSignInResult;
import com.skovdev.springlearn.dto.mapper.UserMapper;
import com.skovdev.springlearn.dto.user.GetUserDto;
import com.skovdev.springlearn.dto.user.SignUpUserDto;
import com.skovdev.springlearn.model.google.GoogleUser;
import com.skovdev.springlearn.security.JwtService;
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
        Optional<GetUserDto> user = userService.getUser(googleUser.getEmail());
        if (!user.isPresent()) {
            SignUpUserDto newUser = UserMapper.toDto(googleUser);
            //XXX: Using randomly generated password for new users created via Google signin
            GetUserDto createdUser = userService.registerNewUser(newUser);
            String jwtToken = jwtService.createJwtToken(newUser.getLogin(), createdUser.getRoles());
            return new SocialSignInResult(jwtToken, true);
        } else {
            String jwtToken = jwtService.createJwtToken(googleUser.getEmail(), user.get().getRoles());
            return new SocialSignInResult(jwtToken, true);
        }

    }

}
