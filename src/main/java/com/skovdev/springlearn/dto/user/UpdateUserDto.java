package com.skovdev.springlearn.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@Accessors(chain = true)
public class UpdateUserDto {

    @NotNull
    private long id;

    @NotNull
    @Size(min=2, max=50)
    private String name;

    @Nullable
    private LocalDate birthdayDate;

    @Nullable
    private String avatarThumbnailUrl;

    @Nullable
    private String avatarFullsizeUrl;

}
