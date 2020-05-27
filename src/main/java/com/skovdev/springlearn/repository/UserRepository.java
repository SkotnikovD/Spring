package com.skovdev.springlearn.repository;

import com.skovdev.springlearn.model.User;

import java.util.Optional;

public interface UserRepository {
    /**
     * @throws com.skovdev.springlearn.error.exceptions.ObjectAlreadyExistsException if user with such {@link User#getLogin()} already exists
     */
    void createUser(User user);

    Optional<User> getUser(String login);

    User updateUser(User userModel);
}
