package com.TrackMyItem.util;

import com.TrackMyItem.dto.*;
import com.TrackMyItem.entity.ItemEntity;
import com.TrackMyItem.entity.RequestEntity;
import com.TrackMyItem.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EntityDtoConverter {
    private final ModelMapper modelMapper;

    //Item
    public ItemEntity convertItemDtoToItemEntity(ItemDto itemDto) {
        return modelMapper.map(itemDto, ItemEntity.class);
    }
    public ItemDto convertItemEntityToItemDto(ItemEntity itemEntity) {
        return modelMapper.map(itemEntity, ItemDto.class);
    }
    public List<ItemDto> toItemDtoList(List<ItemEntity> itemEntityList) {
        return modelMapper.map(itemEntityList, new TypeToken<List<ItemDto>>(){}.getType());
    }

    //Request
    public RequestEntity convertRequestDtoToRequestEntity(RequestDto requestDto) {
        return modelMapper.map(requestDto, RequestEntity.class);
    }
    public RequestDto convertRequestEntityToRequestDto(RequestEntity requestEntity) {
        return modelMapper.map(requestEntity, RequestDto.class);
    }
    public List<RequestDto> toRequestDtoList(List<RequestEntity> requestEntityList) {
        return modelMapper.map(requestEntityList, new TypeToken<List<RequestDto>>(){}.getType());
    }
    public List<RequestAllDetailsDto> toRequestAllDetailsDtoList(List<RequestEntity> requestEntityList) {
        return modelMapper.map(requestEntityList, new TypeToken<List<RequestDto>>(){}.getType());
    }

    //User
    public UserEntity convertUserDtoToUserEntity(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }
    public UserDto convertUserEntityToUserDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }
    public List<UserDto> toUserDtoList(List<UserEntity> userEntityList) {
        return modelMapper.map(userEntityList, new TypeToken<List<UserDto>>(){}.getType());
    }
    public List<UserAllDto> toUserAllDtoList(List<UserEntity> userEntityList) {
        return modelMapper.map(userEntityList, new TypeToken<List<UserAllDto>>(){}.getType());
    }

}
