package com.skovdev.springlearn.service;

import com.skovdev.springlearn.dto.UserDto;
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

    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private FilesRepository filesRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, FilesRepository filesRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.filesRepository = filesRepository;
    }

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = toModel(userDto);
        user.setRoles(Role.of(Role.ROLE_USER));
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        try {
            userRepository.createUser(user);
        } catch (DuplicateKeyException e) {
            throw new ObjectAlreadyExistsException("User with login " + user.getLogin() + " already exists", e);
        }
        return getUser(user.getLogin(), true).get();
    }

    @Override
    public Optional<UserDto> getUser(String login, boolean isExcludePass) {
        Optional<UserDto> userDto = toDto(userRepository.getUser(login));
        if (isExcludePass) {
            userDto.ifPresent(user -> user.setPassword(null));
        }
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto user){
        User userModel = toModel(user);
        User updatedUser = userRepository.updateUser(userModel);
        return toDto(updatedUser);
    }

    @Override
    public UserDto getCurrentUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (username == null) return null;
        return this.getUser(username, true).orElse(null);
    }


    /**
     * Add avatar to current user
     * @param avatar - user avatar
     * @return url to avatar's thumbnail
     */
    @Override
    public String addAvatar(MultipartFile avatar) {
        FilesRepository.SaveImageResponse saveImageResponse = filesRepository.saveImageWithThumbnail(avatar);
        UserDto currentUser = getCurrentUser();
        currentUser.setAvatarFullsizeUrl(saveImageResponse.getFullsizeImage().toString());
        currentUser.setAvatarThumbnailUrl(saveImageResponse.getThumbnailImage().toString());
        return updateUser(currentUser).getAvatarThumbnailUrl();
    }


}
