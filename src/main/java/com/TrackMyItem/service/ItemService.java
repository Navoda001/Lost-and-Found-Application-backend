package com.TrackMyItem.service;

import com.TrackMyItem.dto.ItemDto;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

import java.util.List;

public interface ItemService {
    void addItem(ItemDto itemDto);
    void foundItem(String itemId, ItemDto itemDto);
    void deleteItem(String itemId);
    ItemDto getItemById(String itemId);
    List<ItemDto> getAllItems();
}
