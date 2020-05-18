package com.skovdev.springlearn.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skovdev.springlearn.dto.user.GetUserDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class PostWithAuthorDto {

    private Long postId;
    private String text;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date createdDate;
    private GetUserDto author;
}
