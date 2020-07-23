package com.skovdev.springlearn.model.google;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GoogleUser {

    String email;

    String userId;

    String userName;

    /**
     * Never is empty string: always url or null
     */
    String avatarUrl;

    Boolean isEmailVerified;

}
