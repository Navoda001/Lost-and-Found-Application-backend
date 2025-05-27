package com.TrackMyItem.service;

import com.TrackMyItem.dto.ItemDto;
import com.TrackMyItem.dto.RequestAllDetailsDto;
import com.TrackMyItem.dto.RequestDto;
import com.TrackMyItem.dto.RequestItemDto;

import java.util.List;

public interface RequestService {
    void addRequest(RequestDto requestDto);
    void updateRequest(String requestId,RequestDto requestDto);
    void deleteRequest(String requestId);
    RequestDto getRequestById(String requestId);
    List<RequestDto> getAllRequests();
    List<RequestItemDto> getAllItems();
    List<RequestAllDetailsDto> getAllRequestsByItemId(String itemId);
    List<RequestAllDetailsDto> getAllRequestsByEmail(String email);
}
