package com.skovdev.springlearn.repository;

import com.skovdev.springlearn.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


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
        //TODO Fock! We forgot 'bout ROLE field!!!
    }

    @Override
    public Optional<User> getUser(String login) {
        List<User> user = jdbcTemplate.query(GET_USER_BY_LOGIN, new BeanPropertyRowMapper<>(User.class), login);
        return !user.isEmpty() ? Optional.of(user.get(0)) : Optional.empty();

    }

    @Override
    public Optional<User> getUser(long id) {
        List<User> user = jdbcTemplate.query(GET_USER_BY_ID, new BeanPropertyRowMapper<>(User.class), id);
        return !user.isEmpty() ? Optional.of(user.get(0)) : Optional.empty();
    }

    @Override
    public User updateUser(User userModel) {
        jdbcTemplate.update(UPDATE_USER_BY_LOGIN,
                userModel.getLogin(),
                userModel.getFirstName(),
                userModel.getBirthdayDate(),
                userModel.getAvatarFullsizeUrl(),
                userModel.getAvatarThumbnailUrl(),
                userModel.getLogin());
        return getUser(userModel.getLogin()).get();
    }

    private static final String INSERT_USER = "insert into users (first_name, birthday_date, login, password) values (?, ?, ?, ?)";
    private static final String GET_USER_BY_LOGIN = "select * from users where login=?";
    private static final String GET_USER_BY_ID = "select * from users where user_id=?";
    private static final String UPDATE_USER_BY_LOGIN = "update users set (login, first_name, birthday_date, avatar_fullsize_url, avatar_thumbnail_url) = (?,?,?,?,?) where login=?";

}
