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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto) {
        if (userDto == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.addUser(userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestParam("userId") String userId) {
        if (userId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            userService.deleteUser(userId);
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
    public ResponseEntity<Void> updateUser(@RequestParam("userId") String userId, @RequestBody UserDto userDto) {
        if (userDto == null || userId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            userService.updateUser(userId, userDto);
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

    @GetMapping
    public ResponseEntity<UserDto> getUser(@RequestParam("userId") String userId) {
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

