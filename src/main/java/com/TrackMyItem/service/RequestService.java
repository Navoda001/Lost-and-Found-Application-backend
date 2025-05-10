package com.TrackMyItem.service;

import com.TrackMyItem.dto.RequestDto;

import java.util.List;

public interface RequestService {
    void addRequest(RequestDto requestDto);
    void updateRequest(String requestId,RequestDto requestDto);
    void deleteRequest(String requestId);
    RequestDto getRequestById(String requestId);
    List<RequestDto> getAllRequests();
}
