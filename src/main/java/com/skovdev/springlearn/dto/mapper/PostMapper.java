package com.skovdev.springlearn.dto.mapper;

import com.skovdev.springlearn.dto.PostDto;
import com.skovdev.springlearn.model.Post;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class PostMapper {

    public PostDto toDto(Post post) {
        return new PostDto()
                .setId(post.getPostId())
                .setCreatedDate(post.getCreatedDate())
                .setText(post.getText());
    }

    public static Post toModel(PostDto postDto, Long authorId) {
        return new Post()
                .setText(postDto.getText())
                .setAuthorId(authorId)
                .setCreatedDate(Instant.now().truncatedTo(ChronoUnit.SECONDS));
    }
}
