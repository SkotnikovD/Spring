package com.skovdev.springlearn.repository;

import com.skovdev.springlearn.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepository {


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PostRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Post> getPosts() {
        List<Post> posts = jdbcTemplate.query(GET_POSTS, new BeanPropertyRowMapper<>(Post.class));
        return posts;
    }

    @Override
    public long createPost(Post post) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        ZonedDateTime zdt = post.getCreatedDate().atZone(ZoneId.systemDefault());
        long createdAtMillis = zdt.toInstant().toEpochMilli();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CREATE_POST, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, post.getText());
            ps.setLong(2, post.getAuthorId());
            ps.setTimestamp(3, new Timestamp(createdAtMillis));
            return ps;
        }, keyHolder);

        return (int) keyHolder.getKeys().get("POST_ID");
    }

    private static String GET_POSTS = "SELECT " +
            "post_id as id, post_text as text, created_at as createdDate, fk_user_id as authorId " +
            "from posts ORDER BY created_at DESC";
    private static String CREATE_POST = "insert into posts (post_text, fk_user_id, created_at) values (?, ?, ?)";
}
