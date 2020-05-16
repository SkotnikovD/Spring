package com.skovdev.springlearn.repository;

import com.skovdev.springlearn.model.Post;
import com.skovdev.springlearn.model.PostWithAuthor;
import com.skovdev.springlearn.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepository {


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PostRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PostWithAuthor> getPosts() {
        List<PostWithAuthor> posts = jdbcTemplate.query(GET_POSTS, new RowMapper<PostWithAuthor>() {
            @Override
            public PostWithAuthor mapRow(ResultSet rs, int rowNum) throws SQLException {
                PostWithAuthor p = new BeanPropertyRowMapper<>(PostWithAuthor.class).mapRow(rs, rowNum);
                User u = new BeanPropertyRowMapper<>(User.class).mapRow(rs, rowNum);
                p.setAuthor(u);
                return p;
            }
        });
        return posts;
    }

    @Override
    public long createPost(Post post) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CREATE_POST, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, post.getText());
            ps.setLong(2, post.getAuthorId());
            ps.setTimestamp(3, new Timestamp(post.getCreatedDate().toEpochMilli()));
            return ps;
        }, keyHolder);

        return (int) keyHolder.getKeys().get("POST_ID");
    }

    @Override
    public boolean deletePost(long id) {
        int result = jdbcTemplate.update(DELETE_POST, id);
        return result == 1;
    }

    private static String GET_POSTS = "SELECT post_id, post_text as text, created_at as createdDate, user_id, first_name, birthday_date, avatar_fullsize_url, avatar_thumbnail_url " +
            "from posts join users " +
            "ON posts.fk_user_id = users.user_id " +
            "ORDER BY posts.created_at DESC";
    private static String CREATE_POST = "insert into posts (post_text, fk_user_id, created_at) values (?, ?, ?)";
    private static String DELETE_POST = "delete from posts where post_id=?";
}
