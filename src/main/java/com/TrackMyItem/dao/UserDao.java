package com.TrackMyItem.dao;

import com.TrackMyItem.entity.ItemEntity;
import com.TrackMyItem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserEntity,String> {
    UserEntity findTopByOrderByUserIdDesc();
}
