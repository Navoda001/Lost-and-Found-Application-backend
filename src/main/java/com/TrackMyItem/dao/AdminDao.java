package com.TrackMyItem.dao;

import com.TrackMyItem.entity.AdminEntity;
import com.TrackMyItem.entity.secure.AllUsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminDao extends JpaRepository<AdminEntity, String> {
    Optional<AdminEntity> findByEmail(String email);
    AdminEntity findTopByOrderByAdminIdDesc();
}
