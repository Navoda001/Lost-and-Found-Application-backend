package com.TrackMyItem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestAllDetailsDto {
    private String requestId;
    private String itemId;
    private String itemName;
    private String userId;
    private String userName;
    private RequestStatuses requestStatus;
    private String image;
    private String message;
    private LocalDate requestDate;
    private LocalDate decisionDate;
    private String getDecisionBy;
}
