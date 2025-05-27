package com.TrackMyItem.controller;

import com.TrackMyItem.dto.ItemDto;
import com.TrackMyItem.exception.ItemNotFoundException;
import com.TrackMyItem.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addItem(@RequestBody ItemDto itemDto) {
        if(itemDto== null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        itemService.addItem(itemDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteItem(@RequestParam("itemId") String itemId) {
        if(itemId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try{
            itemService.deleteItem(itemId);
            return ResponseEntity.noContent().build();
        }catch (ItemNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateItem(@RequestParam("itemId") String itemId ,@RequestBody ItemDto itemDto) {
        if(itemDto==null || itemId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            itemService.foundItem(itemId, itemDto);
            return ResponseEntity.noContent().build();
        }catch (ItemNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ItemDto> getItemById(@RequestParam("itemId") String itemId) {
        if(itemId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            ItemDto itemDto = itemService.getItemById(itemId);
            return new ResponseEntity<>(itemDto, HttpStatus.OK);
        }
        catch (ItemNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getItemsByEmail")
    public ResponseEntity<List<ItemDto>> getItemsByEmail(@RequestParam("email") String email) {
        if(email == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(itemService.getItemsByEmail(email), HttpStatus.OK);
        }
        catch (ItemNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getAllItems")
    public ResponseEntity<List<ItemDto>> getAllItems() {
        return new ResponseEntity<>(itemService.getAllItems(), HttpStatus.OK);
    }
}


