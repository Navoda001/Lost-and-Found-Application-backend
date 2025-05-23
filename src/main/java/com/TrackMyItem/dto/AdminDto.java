package com.TrackMyItem.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminDto implements Serializable {
    private String adminId;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate lastLogin;
    private LocalDate updatedAt;
    private Role role;
}
