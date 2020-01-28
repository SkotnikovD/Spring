package com.skovdev.springlearn.repository;

import com.skovdev.springlearn.model.User;

import java.util.Optional;

public interface UserRepository {
    void createUser(User user);

    Optional<User> getUser(String login);
}
