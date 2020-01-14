package com.skovdev.springlearn.repository;

import com.skovdev.springlearn.model.Role;
import com.skovdev.springlearn.model.User;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;


@Repository
public class UserRepositoryImpl implements UserRepository {
    private Map<String, User> mockDatabase = new HashMap<>();

    @SneakyThrows
    @Autowired
    public UserRepositoryImpl(BCryptPasswordEncoder passwordEncoder) {
        mockDatabase.put("a@a.com", new User()
                .setLogin("a@a.com")
                .setPassword(passwordEncoder.encode("pass"))
                .setRoles(new HashSet<Role>(Arrays.asList(new Role("USER"))))
                .setName("John")
                .setBirthday(new SimpleDateFormat("dd/MM/yyyy").parse("20/12/1990")));
    }

    @Override
    public User createUser(User user) {
        mockDatabase.put(user.getLogin(), user);
        return user;
        //TODO saving logic
    }

    @Override
    public Optional<User> getUser(String login) {
        return Optional.ofNullable(mockDatabase.get(login));

    }
}
