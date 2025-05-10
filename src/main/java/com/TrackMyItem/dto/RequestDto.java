package com.TrackMyItem.dto;

import java.time.LocalDate;

public class RequestDto {
    private String requestId;
    private String itemId;
    private String userId;
    public RequestStatuses requestStatus;
    public String message;
    public LocalDate requestDate;
    public LocalDate decisionDate;
}