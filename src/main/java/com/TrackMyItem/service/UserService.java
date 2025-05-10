package com.TrackMyItem.service;

import com.TrackMyItem.dto.UserDto;

public interface UserService {
    void addUser(UserDto userDto);
    void updateUser(String userId,UserDto userDto);
    void deleteUser(String userId);
    UserDto getUserById(String userId);
}
