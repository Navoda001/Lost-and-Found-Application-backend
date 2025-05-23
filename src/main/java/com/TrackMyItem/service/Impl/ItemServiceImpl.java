package com.TrackMyItem.service.Impl;

import com.TrackMyItem.dao.ItemDao;
import com.TrackMyItem.dao.UserDao;
import com.TrackMyItem.dto.ItemDto;
import com.TrackMyItem.dto.ItemStatuses;
import com.TrackMyItem.entity.ItemEntity;
import com.TrackMyItem.entity.UserEntity;
import com.TrackMyItem.exception.ItemNotFoundException;
import com.TrackMyItem.service.ItemService;
import com.TrackMyItem.util.EntityDtoConverter;
import com.TrackMyItem.util.UtilData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemServiceImpl implements ItemService {

    private final ItemDao itemDao;
    private final EntityDtoConverter entityDtoConverter;
    private final UtilData utilData;
    private final UserDao userDao;

    @Override
    public void addItem(ItemDto itemDto) {
    if(itemDto.getItemStatus() == ItemStatuses.FOUND){
        itemDto.setItemId(utilData.generateItemId());
        itemDto.setReportedBy(itemDto.getReportedBy());
        itemDto.setReportedDate(utilData.generateTodayDate());
        itemDto.setFoundDate(utilData.generateTodayDate());
        itemDto.setFoundBy(itemDto.getReportedBy());
    }else{
        itemDto.setItemId(utilData.generateItemId());
        itemDto.setReportedBy(itemDto.getReportedBy());
        itemDto.setReportedDate(utilData.generateTodayDate());

    }

    itemDao.save(entityDtoConverter.convertItemDtoToItemEntity(itemDto));

    }

    @Override
    public void foundItem(String itemId, ItemDto itemDto) {

        Optional<ItemEntity> foundItem = itemDao.findById(itemId);
        Optional<UserEntity> foundUser = userDao.findByEmail(itemDto.getFoundBy());
        if(!foundItem.isPresent()) {
            throw new ItemNotFoundException("Item Not Found");
        }

        foundItem.get().setItemStatus(ItemStatuses.FOUND);

            foundItem.get().setFoundDate(utilData.generateTodayDate());
            foundItem.get().setFoundBy(foundUser.get().getUserId());


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
