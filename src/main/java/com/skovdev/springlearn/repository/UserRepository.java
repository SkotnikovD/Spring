package com.skovdev.springlearn.repository;

import com.skovdev.springlearn.model.User;

import java.util.Optional;

public interface UserRepository {
    User createUser(User user, String password);

    Optional<User> getUser(String login);
}
