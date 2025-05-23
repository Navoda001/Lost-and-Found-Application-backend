package com.TrackMyItem.dao;

import com.TrackMyItem.entity.ItemEntity;
import com.TrackMyItem.entity.StaffEntity;
import com.TrackMyItem.entity.secure.AllUsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffDao extends JpaRepository<StaffEntity,String> {
    Optional<StaffEntity> findByEmail(String email);
    StaffEntity findTopByOrderByStaffIdDesc();
}
