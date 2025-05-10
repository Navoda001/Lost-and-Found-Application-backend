package com.TrackMyItem.entity;

import com.TrackMyItem.dto.ItemStatuses;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Item")
public class ItemEntity {
    @Id
    private String itemId;
    private String itemName;
    private String itemDescription;
    private String location;
    @Enumerated(EnumType.STRING)
    private ItemStatuses itemStatus;
    private LocalDate reportedDate;
    private byte[] image;
    private String reportedBy;
    private String claimedBy;
    private LocalDate claimedDate;
    @OneToMany(mappedBy = "Item",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<RequestEntity> requests;
}
