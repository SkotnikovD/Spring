package com.skovdev.springlearn.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Accessors(chain = true)
public class CreatePostDto {

    @NotBlank
    @Size(min = 1, max=1000000)
    @ApiModelProperty (value = "Post content", required = true)
    private String text;

}
