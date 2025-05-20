package com.TrackMyItem.dao;

import com.TrackMyItem.entity.ItemEntity;
import com.TrackMyItem.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestDao extends JpaRepository<RequestEntity,String> {
    RequestEntity findTopByOrderByRequestIdDesc();
    @Query("SELECT DISTINCT r.item.itemId FROM RequestEntity r")
    List<String> findDistinctItemIds();

    List<RequestEntity> findByItem_ItemId(String itemId);

    int countByItem_ItemId(String itemId); // ✅ This will work

}
