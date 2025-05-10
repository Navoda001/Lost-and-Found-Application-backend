package com.TrackMyItem.dao;

import com.TrackMyItem.entity.ItemEntity;
import com.TrackMyItem.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestDao extends JpaRepository<RequestEntity,String> {
    RequestEntity findTopByOrderByRequestIdDesc();
}
