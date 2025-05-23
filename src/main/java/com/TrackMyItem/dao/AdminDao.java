package com.TrackMyItem.dao;

import com.TrackMyItem.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminDao extends JpaRepository<AdminEntity, String> {
    AdminEntity findTopByOrderByAdminIdDesc();
}
