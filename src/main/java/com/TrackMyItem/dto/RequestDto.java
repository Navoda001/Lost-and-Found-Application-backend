package com.TrackMyItem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestDto implements Serializable {
    private String requestId;
    private String itemId;
    private String userId;
    private RequestStatuses requestStatus;
    private String message;
    private LocalDate requestDate;
    private LocalDate decisionDate;
}