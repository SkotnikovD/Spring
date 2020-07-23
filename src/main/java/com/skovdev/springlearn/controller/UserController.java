package com.skovdev.springlearn.controller;

import com.google.common.collect.ImmutableSet;
import com.skovdev.springlearn.dto.user.GetFullUserDto;
import com.skovdev.springlearn.dto.user.SignUpUserDto;
import com.skovdev.springlearn.dto.user.UpdateUserDto;
import com.skovdev.springlearn.error.exceptions.NoSuchObjectException;
import com.skovdev.springlearn.error.exceptions.RestApiException;
import com.skovdev.springlearn.error.handler.model.ApiError;
import com.skovdev.springlearn.service.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    public static final long MAXIMUM_AVATAR_SIZE = 10_000_000; //10Mb
    private static final Set<String> VALID_AVATAR_TYPES = ImmutableSet.of("image/jpeg", "image/png");

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiResponses(value = {@ApiResponse(code = 404, message = "There is no user with login = 'input login'", response = ApiError.class)})
    @GetMapping()
    public GetFullUserDto getUser(@RequestParam("login") String login) {
        Optional<GetFullUserDto> user = userService.getUser(login);
        return user.orElseThrow(() -> new NoSuchObjectException("There is no user with login = " + login));
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public GetFullUserDto createUser(@RequestBody @Valid SignUpUserDto signupUserDto) {
        return userService.registerNewUser(signupUserDto);
    }

    @GetMapping("/current")
    public GetFullUserDto getCurrentUser() {
        return userService.getCurrentUser();
    }

    /**
     * @param image desired avatar image. Maximum image size is specified by spring.servlet.multipart.max-file-size property.
     * @return avatar thumbnail url
     */
    @PostMapping("/current/avatar")
    public void addAvatar(@RequestParam("image") MultipartFile image) {
        String contentType = image.getContentType();
        if (contentType == null) {
            String supportedTypes = VALID_AVATAR_TYPES.stream().map(Object::toString).collect(Collectors.joining(","));
            throw new RestApiException(HttpStatus.BAD_REQUEST, "Bad request: no content-type image property was found. This endpoint accepts only following types: " + supportedTypes, null);
        }
        if (!VALID_AVATAR_TYPES.contains(contentType)) {
            String supportedTypes = VALID_AVATAR_TYPES.stream().map(Object::toString).collect(Collectors.joining(","));
            throw new RestApiException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Unsupported image type. Found: " + contentType + " supported: " + supportedTypes, null);
        }
        userService.addAvatar(image);
    }

    @PutMapping("/current")
    public GetFullUserDto updateCurrentUser(@RequestBody @Valid UpdateUserDto userDto) {
        return userService.updateCurrentUser(userDto);
    }
}

