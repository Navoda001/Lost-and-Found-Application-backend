package com.TrackMyItem.service.secure;

import com.TrackMyItem.dto.UserDto;
import com.TrackMyItem.dto.secure.AllUsersDto;
import com.TrackMyItem.dto.secure.JWTAuthResponse;
import com.TrackMyItem.dto.secure.PasswordDto;
import com.TrackMyItem.dto.secure.SignIn;

public interface AuthService {
    JWTAuthResponse signIn(SignIn signIn);
    JWTAuthResponse signUp(AllUsersDto allUsersDto);
    void updateUserPassword(PasswordDto allUsersDto);
}
