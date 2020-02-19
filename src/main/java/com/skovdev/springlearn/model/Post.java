package com.skovdev.springlearn.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class Post {

    private Long id;
    private String text;
    private LocalDateTime createdDate;
    private Long authorId;

}
