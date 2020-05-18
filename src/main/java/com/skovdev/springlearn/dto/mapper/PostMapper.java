package com.skovdev.springlearn.dto.mapper;

import com.skovdev.springlearn.dto.CreatePostDto;
import com.skovdev.springlearn.dto.PostWithAuthorDto;
import com.skovdev.springlearn.model.Post;
import com.skovdev.springlearn.model.PostWithAuthor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class PostMapper {

    public static Post toModel(CreatePostDto createPostDto, long authorId, Instant createdDate) {
        return new Post()
                .setText(createPostDto.getText())
                .setAuthorId(authorId)
                .setCreatedDate(createdDate);
    }

    public static PostWithAuthorDto toDto(PostWithAuthor postWithAuthor) {
        return new PostWithAuthorDto()
                .setPostId(postWithAuthor.getPostId())
                .setText(postWithAuthor.getText())
                .setCreatedDate(postWithAuthor.getCreatedDate())
                .setAuthor(UserMapper.toDto(postWithAuthor.getAuthor()));
    }

    public static Collection<PostWithAuthorDto> toDto(Collection<PostWithAuthor> postWithAuthor) {
        return postWithAuthor.stream().map(PostMapper::toDto).collect(Collectors.toList());
    }

    }
