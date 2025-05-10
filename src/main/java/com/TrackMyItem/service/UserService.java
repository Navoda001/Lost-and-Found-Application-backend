package com.TrackMyItem.service;

import com.TrackMyItem.dto.UserAllDto;
import com.TrackMyItem.dto.UserDto;

import java.util.List;

public interface UserService {
    void addUser(UserDto userDto);
    void updateUser(String userId,UserDto userDto);
    void deleteUser(String userId);
    UserDto getUserById(String userId);
    List<UserAllDto> getAllUsers();
}
