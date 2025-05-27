package com.TrackMyItem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDto implements Serializable {
    private String itemId;
    private String itemName;
    private String itemDescription;
    private String location;
    private String email;
    private ItemStatuses itemStatus;
    private LocalDate reportedDate;
    private String image;
    private String reportedBy;
    private String foundBy;
    private LocalDate foundDate;
    private String claimedBy;
    private LocalDate claimedDate;
}
