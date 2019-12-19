package com.skovdev.springlearn.repository;

import com.skovdev.springlearn.model.User;
import org.springframework.stereotype.Repository;


@Repository
public class UserRepositoryImpl implements UserRepository {
    @Override
    public User createUser(User user) {
        return user;
        //TODO saving logic
    }
}
