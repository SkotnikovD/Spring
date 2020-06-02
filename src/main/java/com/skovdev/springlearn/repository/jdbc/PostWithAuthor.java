package com.skovdev.springlearn.repository.jdbc;

import com.skovdev.springlearn.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
class PostWithAuthor {

    private Long postId;
    private String text;
    private Date createdDate;
    private User author;
}
