package com.TrackMyItem.service.Impl;

import com.TrackMyItem.dao.ItemDao;
import com.TrackMyItem.dto.ItemDto;
import com.TrackMyItem.dto.ItemStatuses;
import com.TrackMyItem.entity.ItemEntity;
import com.TrackMyItem.exception.ItemNotFoundException;
import com.TrackMyItem.service.ItemService;
import com.TrackMyItem.util.EntityDtoConverter;
import com.TrackMyItem.util.UtilData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemServiceImpl implements ItemService {

    private final ItemDao itemDao;
    private final EntityDtoConverter entityDtoConverter;
    private final UtilData utilData;

    @Override
    public void addItem(ItemDto itemDto) {
    itemDto.setItemId(utilData.generateItemId());
    itemDto.setReportedDate(utilData.generateTodayDate());

    itemDao.save(entityDtoConverter.convertItemDtoToItemEntity(itemDto));

    }

    @Override
    public void updateItem(String itemId, ItemDto itemDto) {

        Optional<ItemEntity> foundItem = itemDao.findById(itemId);
        if(!foundItem.isPresent()) {
            throw new ItemNotFoundException("Item Not Found");
        }
        foundItem.get().setItemName(itemDto.getItemName());
        foundItem.get().setItemDescription(itemDto.getItemDescription());
        foundItem.get().setItemStatus(itemDto.getItemStatus());
        foundItem.get().setLocation(itemDto.getLocation());
        foundItem.get().setImage(itemDto.getImage());
        foundItem.get().setItemStatus(itemDto.getItemStatus());

        if(itemDto.getItemStatus()==ItemStatuses.FOUND){
            foundItem.get().setFoundDate(utilData.generateTodayDate());
        }else{
            foundItem.get().setClaimedDate(utilData.generateTodayDate());
        }

    }

    @Override
    public void deleteItem(String itemId) {
        Optional<ItemEntity> foundItem = itemDao.findById(itemId);
        if(!foundItem.isPresent()) {
            throw new ItemNotFoundException("Item Not Found");
        }
        itemDao.delete(foundItem.get());
    }

    @Override
    public ItemDto getItemById(String itemId) {
        if(! itemDao.findById(itemId).isPresent()) {
            throw new ItemNotFoundException("Item Not Found");
        }
        return entityDtoConverter.convertItemEntityToItemDto(itemDao.getReferenceById(itemId));

    }

    @Override
    public List<ItemDto> getAllItems() {
        return entityDtoConverter.toItemDtoList(itemDao.findAll());
    }
}
