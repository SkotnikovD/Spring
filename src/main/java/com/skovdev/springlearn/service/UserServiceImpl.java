package com.skovdev.springlearn.service;

import com.skovdev.springlearn.dto.user.GetUserDto;
import com.skovdev.springlearn.dto.user.SignUpUserDto;
import com.skovdev.springlearn.dto.user.UpdateUserDto;
import com.skovdev.springlearn.error.exceptions.ObjectAlreadyExistsException;
import com.skovdev.springlearn.model.Role;
import com.skovdev.springlearn.model.User;
import com.skovdev.springlearn.repository.FilesRepository;
import com.skovdev.springlearn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static com.skovdev.springlearn.dto.mapper.UserMapper.toDto;
import static com.skovdev.springlearn.dto.mapper.UserMapper.toModel;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final FilesRepository filesRepository;
    private final CurrentPrincipalInfoService currentPrincipalInfoService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, FilesRepository filesRepository, CurrentPrincipalInfoService currentPrincipalInfoService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.filesRepository = filesRepository;
        this.currentPrincipalInfoService = currentPrincipalInfoService;
    }

    /**
     * Registers new user with {@link Role#ROLE_USER} role
     *
     * @param signUpUserDto - user to sign up
     * @return created user
     * @throws ObjectAlreadyExistsException if user with such login already exists
     */
    @Override
    public GetUserDto registerNewUser(SignUpUserDto signUpUserDto) {
        User user = toModel(signUpUserDto);
        user.setRoles(Role.ROLE_USER);
        user.setPassword(bCryptPasswordEncoder.encode(signUpUserDto.getPassword()));
        try {
            userRepository.createUser(user);
        } catch (DuplicateKeyException e) {
            throw new ObjectAlreadyExistsException("User with login " + user.getLogin() + " already exists", e);
        }
        return getUser(user.getLogin()).get();
    }

    @Override
    public Optional<GetUserDto> getUser(String login) {
        return toDto(userRepository.getUser(login));
    }

    @Override
    public GetUserDto updateCurrentUser(UpdateUserDto user) {
        User userModel = toModel(user, currentPrincipalInfoService.getCurrentUserLogin());
        User updatedUser = userRepository.updateUser(userModel);
        return toDto(updatedUser);
    }

    @Override
    public GetUserDto getCurrentUser() {
        String username = getCurrentUserUsername();
        if (username == null) return null;
        return this.getUser(username).orElse(null);
    }

    public String getCurrentUserUsername() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    /**
     * Add avatar to current user
     *
     * @param avatar - user avatar
     * @return url to avatar's thumbnail
     */
    @Override
    public String addAvatar(MultipartFile avatar) {
        FilesRepository.SaveImageResponse saveImageResponse = filesRepository.saveImageWithThumbnail(avatar);
        User user = userRepository.getUser(getCurrentUserUsername()).get();
        user.setAvatarThumbnailUrl(saveImageResponse.getThumbnailImage().toString());
        user.setAvatarFullsizeUrl(saveImageResponse.getFullsizeImage().toString());
        return userRepository.updateUser(user).getAvatarThumbnailUrl();
    }


}
