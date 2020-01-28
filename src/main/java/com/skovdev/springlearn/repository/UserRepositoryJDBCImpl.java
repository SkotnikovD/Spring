package com.skovdev.springlearn.repository;

import com.skovdev.springlearn.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Primary
@Repository
public class UserRepositoryJDBCImpl implements UserRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryJDBCImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createUser(User user) {
        jdbcTemplate.update(INSERT_USER,
                user.getFirstName(), user.getBirthdayDate(), user.getLogin(), user.getPassword()
        );
        //TODO correctly translate exception when user with such login already exists. Read about Spring automatic DB exception translation.
    }

    @Override
    public Optional<User> getUser(String login) {
        List<User> user = jdbcTemplate.query(GET_USER_BY_LOGIN, new BeanPropertyRowMapper<>(User.class), login);
        return user!=null && !user.isEmpty() ? Optional.of(user.get(0)) : Optional.empty();

    }

    private static String INSERT_USER = "insert into users (first_name, birthday_date, login, password) values (?, ?, ?, ?)";
    private static String GET_USER_BY_LOGIN = "select * from users where login=?";
}
