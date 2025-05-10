package com.TrackMyItem.service.Impl;

import com.TrackMyItem.dao.RequestDao;
import com.TrackMyItem.dto.RequestDto;
import com.TrackMyItem.dto.RequestStatuses;
import com.TrackMyItem.entity.RequestEntity;
import com.TrackMyItem.exception.RequestNotFoundException;
import com.TrackMyItem.service.RequestService;
import com.TrackMyItem.util.EntityDtoConverter;
import com.TrackMyItem.util.UtilData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestDao requestDao;
    private final EntityDtoConverter entityDtoConverter;
    private final UtilData utilData;

    @Override
    public void addRequest(RequestDto requestDto) {
        requestDto.setRequestId(utilData.generateRequestId());
        requestDto.setRequestDate(utilData.generateTodayDate());
        requestDto.setRequestStatus(RequestStatuses.PENDING);
        requestDao.save(entityDtoConverter.convertRequestDtoToRequestEntity(requestDto));

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
}
