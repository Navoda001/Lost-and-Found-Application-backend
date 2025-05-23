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
@Table(name = "admin")
public class AdminEntity {
    @Id
    private String adminId;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate lastLogin;
    private LocalDate updatedAt;
    @Enumerated(EnumType.STRING)
    private Role role;
}
