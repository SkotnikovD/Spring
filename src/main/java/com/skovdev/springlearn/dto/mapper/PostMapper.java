package com.skovdev.springlearn.dto.mapper;

import com.skovdev.springlearn.dto.CreatePostDto;
import com.skovdev.springlearn.dto.PostWithAuthorDto;
import com.skovdev.springlearn.model.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class PostMapper {

    public static Post toModel(CreatePostDto createPostDto, Instant createdDate) {
        return new Post()
                .setText(createPostDto.getText())
                .setCreatedDate(createdDate);
    }

    public static PostWithAuthorDto toDto(Post post) {
        log.trace("Mapping Post to PostWithAuthorDto");
        return new PostWithAuthorDto()
                .setPostId(post.getPostId())
                .setText(post.getText())
                .setCreatedDate(Date.from(post.getCreatedDate()))
                .setAuthor(UserMapper.toDto(post.getAuthor()));
    }

    public static Collection<PostWithAuthorDto> toDto(Collection<Post> posts) {
        return posts.stream().map(PostMapper::toDto).collect(Collectors.toList());
    }

}
