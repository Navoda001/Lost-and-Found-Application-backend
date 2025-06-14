package com.TrackMyItem.controller;

import com.TrackMyItem.dto.AdminDto;
import com.TrackMyItem.dto.StaffDto;
import com.TrackMyItem.exception.UserNotFoundException;
import com.TrackMyItem.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addAdmin(@RequestBody AdminDto adminDto) {
        logger.info("Call the addAdmin() with param: adminDto={}",adminDto);
        if(adminDto== null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        adminService.addAdmin(adminDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteAdmin(@RequestParam("email") String email) {
        logger.info("Call the deleteAdmin() with param: email={}",email);
        if (email == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            adminService.deleteAdmin(email);
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
    public ResponseEntity<Void> updateAdmin(@RequestBody AdminDto adminDto) {
        logger.info("Call the updateAdmin() with param: adminDto={}",adminDto);
        if (adminDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            adminService.updateAdmin(adminDto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getAdminByEmail")
    public ResponseEntity<AdminDto> getAdminByEmail(@RequestParam("email") String email) {
        logger.info("Call the getAdminByEmail() with param: email={}",email);
        if (email == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            AdminDto adminDto = adminService.getAdminByEmail(email);
            return new ResponseEntity<>(adminDto, HttpStatus.OK);
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
    public ResponseEntity<AdminDto> getAdminById(@RequestParam("adminId") String adminId) {
        logger.info("Call the getAdminById() with param: adminId={}",adminId);
        if (adminId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            AdminDto adminDto = adminService.getAdminById(adminId);
            return new ResponseEntity<>(adminDto, HttpStatus.OK);
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
