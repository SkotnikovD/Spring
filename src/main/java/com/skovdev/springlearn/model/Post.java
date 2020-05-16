package com.skovdev.springlearn.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Setter
@Accessors(chain = true)
public class Post {

    private Long postId;
    private String text;
    private Instant createdDate;
    private Long authorId;

}
