package com.TrackMyItem.service.Impl;

import com.TrackMyItem.dao.ItemDao;
import com.TrackMyItem.dao.RequestDao;
import com.TrackMyItem.dao.UserDao;
import com.TrackMyItem.dto.*;
import com.TrackMyItem.entity.ItemEntity;
import com.TrackMyItem.entity.RequestEntity;
import com.TrackMyItem.entity.StaffEntity;
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
    @Transactional
    public void updateRequest(String requestId, RequestDto requestDto) {
        RequestEntity foundRequest = requestDao.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException("Request Not Found"));

        if (requestDto.getRequestStatus() == RequestStatuses.APPROVED) {
            ItemEntity foundItem = itemDao.findById(foundRequest.getItem().getItemId())
                    .orElseThrow(() -> new RequestNotFoundException("Item Not Found"));


            foundItem.setItemStatus(ItemStatuses.CLAIMED);
            itemDao.save(foundItem);


            List<RequestEntity> requests = requestDao.findByItem_ItemId(foundItem.getItemId());
            for (RequestEntity request : requests) {
                if (!request.getRequestId().equals(foundRequest.getRequestId())) {
                    request.setRequestStatus(RequestStatuses.REJECTED);
                    request.setDecisionDate(utilData.generateTodayDate());
                    request.setMessage("The owner has been found");
                }
            }
            requestDao.saveAll(requests);
        }

        foundRequest.setDecisionDate(utilData.generateTodayDate());
        foundRequest.setRequestStatus(requestDto.getRequestStatus());
        foundRequest.setMessage(requestDto.getMessage());
        requestDao.save(foundRequest);
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
    public List<RequestItemDto> getAllItems() {
        List<String> itemIds = requestDao.findDistinctItemIds();
        List<RequestItemDto> requestItemDtos = new ArrayList<>();

        for (String itemId : itemIds) {
            itemDao.findById(itemId).ifPresent(itemEntity -> {
                ItemDto itemDto = entityDtoConverter.convertItemEntityToItemDto(itemEntity);

                RequestItemDto requestItemDto = new RequestItemDto();
                // Manually copy fields
                requestItemDto.setItemId(itemDto.getItemId());
                requestItemDto.setItemName(itemDto.getItemName());
                requestItemDto.setItemDescription(itemDto.getItemDescription());
                requestItemDto.setLocation(itemDto.getLocation());
                requestItemDto.setItemStatus(itemDto.getItemStatus());
                requestItemDto.setReportedDate(itemDto.getReportedDate());
                requestItemDto.setImage(itemDto.getImage());
                requestItemDto.setReportedBy(itemDto.getReportedBy());
                requestItemDto.setFoundBy(itemDto.getFoundBy());
                requestItemDto.setFoundDate(itemDto.getFoundDate());
                requestItemDto.setClaimedBy(itemDto.getClaimedBy());
                requestItemDto.setClaimedDate(itemDto.getClaimedDate());

                int requestCount = requestDao.countByItem_ItemId(itemId); // Note underscore here!
                requestItemDto.setRequestCount(requestCount);

                requestItemDtos.add(requestItemDto);
            });
        }

        return requestItemDtos;
    }


    @Override
    public List<RequestAllDetailsDto> getAllRequestsByItemId(String itemId) {
        List<RequestEntity> requests = requestDao.findByItem_ItemId(itemId);
        List<RequestAllDetailsDto> dtos = new ArrayList<>();

        for (RequestEntity request : requests) {
            UserEntity user = request.getUser();
            ItemEntity item = request.getItem();
            StaffEntity decisionUser = request.getDecisionUser();

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
            dto.setGetDecisionBy(decisionUser != null ? decisionUser.getStaffId() : null);

            dtos.add(dto);
        }

        return dtos;
    }



}
