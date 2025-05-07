package com.TrackMyItem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDto implements Serializable {
    private String itemId;
    private String itemName;
    private String itemDescription;
    private String location;
    private ItemStatuses itemStatus;
    private LocalDate reportedDate;
    private byte[] image;
    private String reportedBy;
    private String claimedBy;
    private LocalDate claimedDate;
}
