package com.TrackMyItem.controller.secure;

import com.TrackMyItem.dto.secure.AllUsersDto;
import com.TrackMyItem.dto.secure.JWTAuthResponse;
import com.TrackMyItem.dto.secure.SignIn;
import com.TrackMyItem.entity.RequestEntity;
import com.TrackMyItem.service.secure.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("signin")
    public ResponseEntity<JWTAuthResponse> signIn(@RequestBody SignIn signIn){
        return new ResponseEntity<>(authService.signIn(signIn),HttpStatus.OK);
    }
    @PostMapping("signup")
    public ResponseEntity<JWTAuthResponse> signUp(@RequestBody AllUsersDto signUp){
        return new ResponseEntity<>(authService.signUp(signUp),HttpStatus.CREATED);
    }
    @PostMapping("changePassword")
    public ResponseEntity<JWTAuthResponse> changePassword(@RequestBody AllUsersDto allUsersDto){
        authService.updateUserPassword(allUsersDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
