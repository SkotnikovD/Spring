package com.skovdev.springlearn.repository;

import com.skovdev.springlearn.model.User;

public interface UserRepository {
    User createUser(User user, String password);
}
