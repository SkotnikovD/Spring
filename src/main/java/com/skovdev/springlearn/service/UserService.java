package com.skovdev.springlearn.service;

import com.skovdev.springlearn.dto.UserDto;
import com.skovdev.springlearn.dto.UserWithCredentialsDto;

public interface UserService {
     UserDto registerNewUser(UserWithCredentialsDto userDto);
}
