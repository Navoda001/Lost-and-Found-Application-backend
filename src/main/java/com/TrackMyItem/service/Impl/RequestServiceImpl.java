package com.TrackMyItem.service.Impl;

import com.TrackMyItem.dao.ItemDao;
import com.TrackMyItem.dao.RequestDao;
import com.TrackMyItem.dao.UserDao;
import com.TrackMyItem.dto.ItemDto;
import com.TrackMyItem.dto.RequestAllDetailsDto;
import com.TrackMyItem.dto.RequestDto;
import com.TrackMyItem.dto.RequestStatuses;
import com.TrackMyItem.entity.ItemEntity;
import com.TrackMyItem.entity.RequestEntity;
import com.TrackMyItem.entity.UserEntity;
import com.TrackMyItem.exception.RequestAlreadyExistsException;
import com.TrackMyItem.exception.RequestNotFoundException;
import com.TrackMyItem.service.RequestService;
import com.TrackMyItem.util.EntityDtoConverter;
import com.TrackMyItem.util.UtilData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestDao requestDao;
    private final EntityDtoConverter entityDtoConverter;
    private final ItemDao itemDao;
    private final UserDao userDao;
    private final UtilData utilData;

    @Override
    public void addRequest(RequestDto requestDto) {
        try{
            requestDto.setRequestId(utilData.generateRequestId());
            requestDto.setRequestDate(utilData.generateTodayDate());
            requestDto.setRequestStatus(RequestStatuses.PENDING);
            requestDao.save(entityDtoConverter.convertRequestDtoToRequestEntity(requestDto));

        } catch (Exception e) {
            throw new RequestAlreadyExistsException("Request already exists");
        }

    }

    @Override
    public void updateRequest(String requestId, RequestDto requestDto) {
        Optional<RequestEntity> foundRequest = requestDao.findById(requestId);
        if(!foundRequest.isPresent()) {
            throw new RequestNotFoundException("Request Not Found");
        }
        foundRequest.get().setDecisionDate(utilData.generateTodayDate());
        foundRequest.get().setRequestStatus(requestDto.getRequestStatus());
        foundRequest.get().setMessage(requestDto.getMessage());
    }

    @Override
    public void deleteRequest(String requestId) {
        Optional<RequestEntity> foundRequest = requestDao.findById(requestId);
        if(!foundRequest.isPresent()) {
            throw new RequestNotFoundException("Request Not Found");
        }
        requestDao.delete(foundRequest.get());
    }

    @Override
    public RequestDto getRequestById(String requestId) {
        Optional<RequestEntity> foundRequest = requestDao.findById(requestId);
        if(!foundRequest.isPresent()) {
            throw new RequestNotFoundException("Request Not Found");
        }
        return entityDtoConverter.convertRequestEntityToRequestDto(foundRequest.get());
    }

    @Override
    public List<RequestDto> getAllRequests() {
        return entityDtoConverter.toRequestDtoList(requestDao.findAll());
    }

    @Override
    public List<ItemDto> getAllItems() {
        List<String> itemIds = requestDao.findDistinctUserIds();
        System.out.println("itemIds" + itemIds);

        List<ItemDto> itemDtos = new ArrayList<>();
        for(String itemId : itemIds) {
            itemDtos.add(entityDtoConverter.convertItemEntityToItemDto(itemDao.findById(itemId).get()));
        }
        return itemDtos;
    }

    @Override
    public List<RequestAllDetailsDto> getAllRequestsByItemId(String itemId) {
        List<RequestEntity> requests = requestDao.findByItem_ItemId(itemId);
        List<RequestAllDetailsDto> dtos = new ArrayList<>();

        for (RequestEntity request : requests) {
            UserEntity user = request.getUser();
            ItemEntity item = request.getItem();
            UserEntity decisionUser = request.getDecisionUser();

            RequestAllDetailsDto dto = new RequestAllDetailsDto();
            dto.setRequestId(request.getRequestId());
            dto.setItemId(item.getItemId());
            dto.setItemName(item.getItemName());
            dto.setUserId(user.getUserId());
            dto.setUserName(user.getFirstName() + " " + user.getLastName());
            dto.setRequestStatus(request.getRequestStatus());
            dto.setMessage(request.getMessage());
            dto.setRequestDate(request.getRequestDate());
            dto.setDecisionDate(request.getDecisionDate());
            dto.setGetDecisionBy(decisionUser != null ? decisionUser.getUserId() : null);

            dtos.add(dto);
        }

        return dtos;
    }



}
