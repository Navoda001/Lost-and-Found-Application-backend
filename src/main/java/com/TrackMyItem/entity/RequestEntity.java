package com.TrackMyItem.entity;

import com.TrackMyItem.dto.RequestStatuses;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Request")
public class RequestEntity {
    @Id
    private String requestId;
    @ManyToOne
    @JoinColumn(name = "itemId",nullable = false)
    private ItemEntity itemId;
    @ManyToOne
    @JoinColumn(name = "userId",nullable = false)
    private UserEntity userId;
    @Enumerated(EnumType.STRING)
    private RequestStatuses requestStatus;
    private String message;
    private LocalDate requestDate;
    private LocalDate decisionDate;
}
