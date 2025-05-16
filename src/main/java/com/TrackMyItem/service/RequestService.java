package com.TrackMyItem.service;

import com.TrackMyItem.dto.ItemDto;
import com.TrackMyItem.dto.RequestAllDetailsDto;
import com.TrackMyItem.dto.RequestDto;

import java.util.List;

public interface RequestService {
    void addRequest(RequestDto requestDto);
    void updateRequest(String requestId,RequestDto requestDto);
    void deleteRequest(String requestId);
    RequestDto getRequestById(String requestId);
    List<RequestDto> getAllRequests();
    List<ItemDto> getAllItems();
    List<RequestAllDetailsDto> getAllRequestsByItemId(String itemId);
}
