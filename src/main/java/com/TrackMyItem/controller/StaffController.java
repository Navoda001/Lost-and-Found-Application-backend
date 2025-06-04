package com.TrackMyItem.controller;

import com.TrackMyItem.dto.ItemDto;
import com.TrackMyItem.dto.StaffDto;
import com.TrackMyItem.dto.UserDto;
import com.TrackMyItem.exception.UserNotFoundException;
import com.TrackMyItem.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/staffs")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;
    private static final Logger logger = LoggerFactory.getLogger(StaffController.class);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addStaff(@RequestBody StaffDto staffDto) {
        logger.info("Call the addStaff() with param: staffDto={}",staffDto);
        if(staffDto== null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        staffService.addStaff(staffDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteStaff(@RequestParam("email") String email) {
        logger.info("Call the deleteStaff() with param: email={}",email);
        if (email == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            staffService.deleteStaff(email);
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

    @PatchMapping(value = "update-image", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateImage(@RequestBody StaffDto staffDto) {
        logger.info("Call the staff updateImage() ");
        if (staffDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            staffService.updateImage(staffDto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "update-staff", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateStaff(@RequestBody StaffDto staffDto) {
        logger.info("Call the updateStaff() with param: staffDto={}",staffDto);
        if (staffDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            staffService.updateStaff(staffDto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getStaffByEmail")
    public ResponseEntity<StaffDto> getStaffByEmail(@RequestParam("email") String email) {
        logger.info("Call the getStaffByEmail() with param: email={}",email);
        if (email == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            StaffDto staffDto = staffService.getStaffByemail(email);
            return new ResponseEntity<>(staffDto, HttpStatus.OK);
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
    public ResponseEntity<StaffDto> getStaffById(@RequestParam("staffId") String staffId) {
        logger.info("Call the getStaffById() with param: staffId={}",staffId);
        if (staffId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            StaffDto staffDto = staffService.getStaffById(staffId);
            return new ResponseEntity<>(staffDto, HttpStatus.OK);
        }catch (UserNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
