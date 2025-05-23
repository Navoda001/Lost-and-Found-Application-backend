package com.TrackMyItem.controller;

import com.TrackMyItem.dto.UserAllDto;
import com.TrackMyItem.dto.UserDto;
import com.TrackMyItem.exception.UserNotFoundException;
import com.TrackMyItem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestBody String email) {
        if (email == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            userService.deleteUser(email);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (UserNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateUser(@RequestBody UserDto userDto) {
        if (userDto == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            userService.updateUser(userDto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (UserNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getUserByEmail")
    public ResponseEntity<UserDto> getUserByEmail(@RequestBody String email) {
        if (email == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            UserDto userDto = userService.getUserByEmail(email);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }catch (UserNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<UserDto> getUserById(@RequestParam("userId") String userId) {
        if (userId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            UserDto userDto = userService.getUserById(userId);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }catch (UserNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getAllUsers")
    public ResponseEntity<List<UserAllDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

}

