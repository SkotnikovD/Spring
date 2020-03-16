package com.skovdev.springlearn.dto.mapper;

import com.skovdev.springlearn.dto.PostDto;
import com.skovdev.springlearn.model.Post;
import com.skovdev.springlearn.model.User;
import com.skovdev.springlearn.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Component
public class PostMapper {

    private UserRepository userRepository;

    public PostMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public PostDto toDto(Post post) {
        Optional<User> postAuthor = userRepository.getUser(post.getAuthorId());
        //TODO inject author name via JOIN, not by calling getUser for every object

        return new PostDto()
                .setId(post.getId())
                .setUserName(postAuthor.get().getFirstName())
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
