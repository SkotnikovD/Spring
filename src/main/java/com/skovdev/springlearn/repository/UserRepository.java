package com.skovdev.springlearn.repository;

import com.skovdev.springlearn.model.User;

import java.util.Optional;

public interface UserRepository {
    /**
     * @throws org.springframework.dao.DuplicateKeyException if user with such {@link User#login} already exists
     */
    void createUser(User user);

    Optional<User> getUser(String login);

    User updateUser(User userModel);
}
