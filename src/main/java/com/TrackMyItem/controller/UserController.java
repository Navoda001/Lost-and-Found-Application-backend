package com.TrackMyItem.controller;

import com.TrackMyItem.dto.StaffDto;
import com.TrackMyItem.dto.UserAllDto;
import com.TrackMyItem.dto.UserDto;
import com.TrackMyItem.exception.UserNotFoundException;
import com.TrackMyItem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestParam String email) {
        logger.info("Call the deleteUser() with param: email={}",email);
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

    @PatchMapping(value = "update-image",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateImage(@RequestBody UserDto userDto) {
        logger.info("Call the user updateImage() ");
        if (userDto == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            userService.updateImage(userDto);
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

    @PatchMapping(value = "update-user",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateUser(@RequestBody UserDto userDto) {
        logger.info("Call the updateUser() with param: userDto={}",userDto);
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
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam String email) {
        logger.info("Call the getUserByEmail() with param: email={}",email);
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
        logger.info("Call the getUserById() with param: userId={}",userId);
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
        logger.info("Call the getAllUsers() ");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

}

