package com.TrackMyItem.controller;

import com.TrackMyItem.dto.RequestDto;
import com.TrackMyItem.exception.RequestNotFoundException;
import com.TrackMyItem.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/requests")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createRequest(@RequestBody RequestDto requestDto) {
        if(requestDto==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        requestService.addRequest(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteRequest(@RequestParam("requestId") String requestId) {

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


    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateRequest (@RequestParam("requestId") String requestId, RequestDto requestDto){
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
        if(requestId==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            requestService.getRequestById(requestId);
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
        return new ResponseEntity<>(requestService.getAllRequests(), HttpStatus.OK);
    }
}
