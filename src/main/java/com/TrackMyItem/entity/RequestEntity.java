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
@Table(name = "request",
        uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "itemId"})
)
public class RequestEntity {
    @Id
    private String requestId;
    @ManyToOne
    @JoinColumn(name = "itemId",nullable = false)
    private ItemEntity item;
    @ManyToOne
    @JoinColumn(name = "userId",nullable = false)
    private UserEntity user;
    @Enumerated(EnumType.STRING)
    private RequestStatuses requestStatus;
    private String message;
    private LocalDate requestDate;
    private LocalDate decisionDate;
    @ManyToOne
    @JoinColumn(name = "getDecisionBy",nullable = true)
    private StaffEntity decisionUser;
}
