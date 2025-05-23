package com.TrackMyItem.service;

import com.TrackMyItem.dto.UserAllDto;
import com.TrackMyItem.dto.UserDto;

import java.util.List;

public interface UserService {
    void updateImage(UserDto userDto);
    void updateUser(UserDto userDto);
    void deleteUser(String userId);
    UserDto getUserById(String userId);
    UserDto getUserByEmail(String userId);
    List<UserAllDto> getAllUsers();
}
