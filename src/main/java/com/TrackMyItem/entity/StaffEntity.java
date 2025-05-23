package com.TrackMyItem.entity;

import com.TrackMyItem.dto.Role;
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
@Table(name = "staff")
public class StaffEntity {
    @Id
    private String staffId;
    private String firstName;
    private String lastName;
    private String email;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;
    private String createdAt;
    private LocalDate lastLogin;
    private LocalDate updatedAt;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "decisionUser")
    private List<RequestEntity> decisionRequests;
}
