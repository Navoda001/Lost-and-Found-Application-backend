package com.TrackMyItem.service.Impl;

import com.TrackMyItem.dao.ItemDao;
import com.TrackMyItem.dao.UserDao;
import com.TrackMyItem.dto.ItemDto;
import com.TrackMyItem.dto.ItemStatuses;
import com.TrackMyItem.entity.ItemEntity;
import com.TrackMyItem.entity.UserEntity;
import com.TrackMyItem.exception.ItemNotFoundException;
import com.TrackMyItem.exception.UserNotFoundException;
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
        System.out.println(itemDto);
        Optional<UserEntity> foundUser = userDao.findByEmail(itemDto.getEmail());
        if(!foundUser.isPresent()){
            throw new UserNotFoundException("user not found");
        }

    if(itemDto.getItemStatus() == ItemStatuses.FOUND){
        itemDto.setItemId(utilData.generateItemId());
        itemDto.setReportedBy(foundUser.get().getUserId());
        itemDto.setReportedDate(utilData.generateTodayDate());
        itemDto.setFoundDate(utilData.generateTodayDate());
        itemDto.setFoundBy(foundUser.get().getUserId());
    }else{
        itemDto.setItemId(utilData.generateItemId());
        itemDto.setReportedBy(foundUser.get().getUserId());
        itemDto.setReportedDate(utilData.generateTodayDate());
    }

    itemDao.save(entityDtoConverter.convertItemDtoToItemEntity(itemDto));

    }

    @Override
    public void foundItem(String itemId, ItemDto itemDto) {

        Optional<ItemEntity> foundItem = itemDao.findById(itemId);
        Optional<UserEntity> foundUser = userDao.findByEmail(itemDto.getEmail());
        if(!foundItem.isPresent()) {
            throw new ItemNotFoundException("Item Not Found");
        }
        if(!foundUser.isPresent()) {
            throw new UserNotFoundException("user not found");
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
    public List<ItemDto> getItemsByEmail(String email) {
        Optional<UserEntity> foundUser = userDao.findByEmail(email);
        if(!foundUser.isPresent()) {
            throw new ItemNotFoundException("User Not Found");
        }
        return entityDtoConverter.toItemDtoList(itemDao.findByReportedBy(foundUser.get().getUserId()));
    }

    @Override
    public List<ItemDto> getAllItems() {
        return entityDtoConverter.toItemDtoList(itemDao.findAll());
    }
}
