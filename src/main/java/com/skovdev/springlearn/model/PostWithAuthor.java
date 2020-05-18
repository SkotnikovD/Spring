package com.skovdev.springlearn.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class PostWithAuthor {

    private Long postId;
    private String text;
    private Date createdDate;
    private User author;
}
