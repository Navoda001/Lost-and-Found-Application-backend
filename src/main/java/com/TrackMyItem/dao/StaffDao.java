package com.TrackMyItem.dao;

import com.TrackMyItem.entity.ItemEntity;
import com.TrackMyItem.entity.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffDao extends JpaRepository<StaffEntity,String> {
    StaffEntity findTopByOrderByStaffIdDesc();
}
