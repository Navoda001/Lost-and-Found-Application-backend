package com.TrackMyItem.dao;

import com.TrackMyItem.entity.ItemEntity;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDao extends JpaRepository<ItemEntity,String> {
    ItemEntity findTopByOrderByItemIdDesc();


}
