package com.TrackMyItem.controller;

import com.TrackMyItem.dto.ItemDto;
import com.TrackMyItem.dto.RequestAllDetailsDto;
import com.TrackMyItem.dto.RequestDto;
import com.TrackMyItem.dto.RequestItemDto;
import com.TrackMyItem.exception.RequestAlreadyExistsException;
import com.TrackMyItem.exception.RequestNotFoundException;
import com.TrackMyItem.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/requests")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;
    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    @PreAuthorize("hasRole('USER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createRequest(@RequestBody RequestDto requestDto) {
        logger.info("Call the createRequest() with param {}",requestDto);
        if(requestDto==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try{
            requestService.addRequest(requestDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (RequestAlreadyExistsException e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteRequest(@RequestParam("requestId") String requestId) {
        logger.info("Call the deleteRequest() with param: requestId={}",requestId);
        if(requestId==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            requestService.deleteRequest(requestId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (RequestNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('STAFF')")
    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateRequest (@RequestParam("requestId") String requestId, @RequestBody RequestDto requestDto){
        logger.info("Call the updateRequest() with params: requestId={}, requestDto={}", requestId, requestDto);
        if(requestId==null || requestDto==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            requestService.updateRequest(requestId, requestDto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (RequestNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<RequestDto> getRequest(@RequestParam("requestId") String requestId){
        logger.info("Call the getRequest() with param: requestId={}",requestId);
        if(requestId==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(requestService.getRequestById(requestId), HttpStatus.OK);
        }catch (RequestNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getAllRequests")
    public ResponseEntity<List<RequestDto>> getAllRequests() {
        logger.info("Call the getAllRequests()");
        return new ResponseEntity<>(requestService.getAllRequests(), HttpStatus.OK);
    }

    @GetMapping("getAllItems")
    public ResponseEntity<List<RequestItemDto>> getAllItems() {
        logger.info("Call the getAllItems()");
        return new ResponseEntity<>(requestService.getAllItems(), HttpStatus.OK);
    }

    @GetMapping("getRequestsByItemId")
    public ResponseEntity<List<RequestAllDetailsDto>>  getRequestsByItemId(@RequestParam("itemId") String itemId){
        logger.info("Call the getRequestsByItemId() with param: itemId={}",itemId);
        if(itemId==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(requestService.getAllRequestsByItemId(itemId), HttpStatus.OK);
        }catch (RequestNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getRequestsByEmail")
    public ResponseEntity<List<RequestAllDetailsDto>>  getAllRequestsByEmail(@RequestParam("email") String email){
        logger.info("Call the getAllRequestsByEmail() with param: email={}",email);
        if(email==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(requestService.getAllRequestsByEmail(email), HttpStatus.OK);
        }catch (RequestNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
