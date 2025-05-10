package com.TrackMyItem.service;

import com.TrackMyItem.dto.ItemDto;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

import java.util.List;

public interface ItemService {
    void addFoundItem(ItemDto itemDto);
    void addLostItem(ItemDto itemDto);
    void updateLostItem(String itemId, ItemDto itemDto);
    void updateFoundItem(String itemId, ItemDto itemDto);
    void deleteItem(String itemId);
    ItemDto getItemById(String itemId);
    List<ItemDto> getAllItems();
}
