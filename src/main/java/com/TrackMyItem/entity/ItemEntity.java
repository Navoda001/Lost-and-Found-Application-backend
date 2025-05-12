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
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;
    private String reportedBy;
    private LocalDate reportedDate;
    private String foundBy;
    private LocalDate foundDate;
    private String claimedBy;
    private LocalDate claimedDate;
    @OneToMany(mappedBy = "item",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<RequestEntity> requests;
}
