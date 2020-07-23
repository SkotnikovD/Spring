package com.skovdev.springlearn.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.common.base.Strings;
import com.skovdev.springlearn.model.google.GoogleUser;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class GoogleSigninService {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String CLIENT_ID;

    private HttpTransport httpTransport;
    private JsonFactory jsonFactory;

    @Autowired
    public GoogleSigninService(HttpTransport httpTransport, JsonFactory jsonFactory) {
        this.httpTransport = httpTransport;
        this.jsonFactory = jsonFactory;
    }

    public GoogleUser verifyTokenAndExtractUserInfo(String idToken) {
        GoogleIdToken googleIdToken = verifyIdToken(idToken);
        if (googleIdToken != null) {
            GoogleIdToken.Payload payload = googleIdToken.getPayload();
            return new GoogleUser()
                    .setEmail(payload.getEmail())
                    .setUserId(payload.getSubject())
                    .setUserName((String) payload.get("name"))
                    .setIsEmailVerified(payload.getEmailVerified())
                    .setAvatarUrl(Strings.emptyToNull((String) payload.get("picture")));

        } else {
            throw new RuntimeException("Google ID token is invalid.");
        }
    }

    @SneakyThrows
    private GoogleIdToken verifyIdToken(String idToken) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();
        return verifier.verify(idToken);
    }

}
