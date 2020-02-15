package com.updatetech.SpringRestAPI.ui.service;

import com.updatetech.SpringRestAPI.ui.Dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);

    UserDto findUser(String email);

    UserDto findUserByUserId(String id);

    UserDto updateUser(String userId, UserDto userDto);

    void deleteUser(String id);

    List<UserDto> getUsers(int page, int limit);
}
