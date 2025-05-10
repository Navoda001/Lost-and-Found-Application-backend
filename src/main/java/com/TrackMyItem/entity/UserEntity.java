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
@Table(name = "User")
public class UserEntity {
    @Id
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private LocalDate joinDate;
    private LocalDate lastLogin;
    private LocalDate updatedAt;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<RequestEntity> requests;
    @OneToMany(mappedBy = "decisionUser")
    private List<RequestEntity> decisionRequests;

}
