package com.TrackMyItem.controller.secure;

import com.TrackMyItem.dto.secure.AllUsersDto;
import com.TrackMyItem.dto.secure.JWTAuthResponse;
import com.TrackMyItem.dto.secure.PasswordDto;
import com.TrackMyItem.dto.secure.SignIn;
import com.TrackMyItem.entity.RequestEntity;
import com.TrackMyItem.exception.PasswordUnmatchException;
import com.TrackMyItem.service.secure.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PatchMapping(("changePassword"))
    public ResponseEntity<Void> changePassword(@RequestBody PasswordDto dto){
        try{
            authService.updateUserPassword(dto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch(PasswordUnmatchException e){
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
