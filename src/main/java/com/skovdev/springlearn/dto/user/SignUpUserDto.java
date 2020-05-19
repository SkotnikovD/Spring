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
public class SignUpUserDto{

    @NotNull
    @Size(min=1, max=50)
    private String login;

    @NotNull
    @Size(min=2, max=50)
    private String name;

    @Nullable
    private LocalDate birthdayDate;

    @NotNull
    @Size(min=3, max=50)
    private String password;
}
