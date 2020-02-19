package com.skovdev.springlearn.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class PostDto {

    private Long id;
    @NotNull
    private String text;
    private String userName;
    private LocalDateTime createdDate;

}
