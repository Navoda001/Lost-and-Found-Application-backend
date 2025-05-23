package com.TrackMyItem.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffDto implements Serializable {
    private String staffId;
    private String firstName;
    private String lastName;
    private String email;
    private String image;
    private LocalDate createdAt;
    private LocalDate lastLogin;
    private LocalDate updatedAt;
    private Role role;
}
