package com.skovdev.springlearn.repository;

import com.skovdev.springlearn.error.exceptions.ObjectAlreadyExistsException;
import com.skovdev.springlearn.model.User;
import lombok.extern.slf4j.Slf4j;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.ResultSetExtractorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.CollectionUtils.isEmpty;


@Slf4j
@Repository
public class UserRepositoryJDBCImpl implements UserRepository {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired
    public UserRepositoryJDBCImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public void createUser(User user) {
        KeyHolder userIdkeyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(
                    new PreparedStatementCreator() {
                        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                            PreparedStatement ps =
                                    connection.prepareStatement(INSERT_USER, new String[]{"user_id"});
                            ps.setString(1, user.getFirstName());
                            ps.setDate(2, user.getBirthdayDate() != null ? Date.valueOf(user.getBirthdayDate()) : null);
                            ps.setString(3, user.getLogin());
                            ps.setString(4, user.getPassword());
                            return ps;
                        }
                    },
                    userIdkeyHolder);
        } catch (DuplicateKeyException e) {
            throw new ObjectAlreadyExistsException("User with login " + user.getLogin() + " already exists", e);
        }

        if (isEmpty(user.getRoles())) {
            log.warn("Created user with login = " + user.getLogin() + " doesn't have any roles");
            return;
        }

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("roles", user.getRoles());
        List<Number> rolesId = namedParameterJdbcTemplate.query("SELECT user_role_id FROM user_roles WHERE role IN (:roles)",
                parameters, (rs, rowNum) -> rs.getLong("user_role_id"));

        if (rolesId.size() != user.getRoles().size()) {
            throw new IllegalArgumentException("Can't find some of user roles in database. User creation aborted. Input user roles: " + user.getRoles().toString());
        }

        Number userId = userIdkeyHolder.getKey();

        for (Number roleId : rolesId) {
            jdbcTemplate.update(INSERT_ROLE_TO_USER, roleId, userId);
        }
    }

    @Override
    public Optional<User> getUser(String login) {

        final ResultSetExtractorImpl<User> resultSetExtractor =
                JdbcTemplateMapperFactory
                        .newInstance()
                        .addKeys("userId")
                        .newResultSetExtractor(User.class);

        List<User> results = jdbcTemplate.query(GET_USER_WITH_ROLES_BY_LOGIN, resultSetExtractor, login);

        return results != null && !results.isEmpty() ? Optional.of(results.get(0)) : Optional.empty();
    }

    @Override
    public User updateUser(User userModel) {
        jdbcTemplate.update(UPDATE_USER_BY_LOGIN,
                userModel.getFirstName(),
                userModel.getBirthdayDate(),
                userModel.getAvatarFullsizeUrl(),
                userModel.getAvatarThumbnailUrl(),
                userModel.getLogin());
        return getUser(userModel.getLogin()).get();
    }

    private static final String INSERT_USER = "insert into users (first_name, birthday_date, login, password) values (?, ?, ?, ?)";
    private static final String GET_USER_WITH_ROLES_BY_LOGIN = "select user_id as userId, first_name, birthday_date, password, login, avatar_fullsize_url, avatar_thumbnail_url, role as roles from users as u " +
            "join users_to_user_roles as utor on u.user_id=utor.user_id_fk " +
            "join user_roles as ur on ur.user_role_id = utor.user_role_id_fk " +
            "where u.login=?";
    private static final String GET_USER_BY_ID = "select * from users where user_id=?";
    private static final String UPDATE_USER_BY_LOGIN = "update users set (first_name, birthday_date, avatar_fullsize_url, avatar_thumbnail_url) = (?,?,?,?) where login=?";
    private static final String INSERT_ROLE_TO_USER = "insert into users_to_user_roles (user_role_id_fk, user_id_fk) values (?, ?)";

}
