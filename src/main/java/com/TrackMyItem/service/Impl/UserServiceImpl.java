package com.TrackMyItem.service.Impl;

import com.TrackMyItem.dao.ItemDao;
import com.TrackMyItem.dao.UserDao;
import com.TrackMyItem.dao.secure.AllUsersDao;
import com.TrackMyItem.dto.UserAllDto;
import com.TrackMyItem.dto.UserDto;
import com.TrackMyItem.dto.secure.AllUsersDto;
import com.TrackMyItem.entity.ItemEntity;
import com.TrackMyItem.entity.UserEntity;
import com.TrackMyItem.entity.secure.AllUsersEntity;
import com.TrackMyItem.exception.ItemNotFoundException;
import com.TrackMyItem.exception.UserNotFoundException;
import com.TrackMyItem.service.UserService;
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
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final EntityDtoConverter entityDtoConverter;
    private final UtilData utilData;
    private final AllUsersDao allUsersDao;

    @Override
    public void updateImage(UserDto userDto) {
        Optional<UserEntity> foundUser = userDao.findByEmail(userDto.getEmail());
        foundUser.get().setImage(userDto.getImage());
        foundUser.get().setUpdatedAt(utilData.generateTodayDate());
    }

    @Override
    public void updateUser(UserDto userDto) {
        Optional<UserEntity> foundUser = userDao.findByEmail(userDto.getEmail());
        if(!foundUser.isPresent()) {
            throw new UserNotFoundException("User Not Found");
        }
        foundUser.get().setFirstName(userDto.getFirstName());
        foundUser.get().setLastName(userDto.getLastName());
        foundUser.get().setPhoneNumber(userDto.getPhoneNumber());

        foundUser.get().setUpdatedAt(utilData.generateTodayDate());
    }
    @Override
    public void deleteUser(String email) {
        Optional<UserEntity> foundUser = userDao.findByEmail(email);
        if(!foundUser.isPresent()) {
            throw new UserNotFoundException("User Not Found");
        }
        Optional<AllUsersEntity> foundedUser = allUsersDao.findByEmail(email);
        if(!foundedUser.isPresent()) {
            throw new UserNotFoundException("User Not Found");
        }
        allUsersDao.delete(foundedUser.get());
        userDao.delete(foundUser.get());
    }

    @Override
    public UserDto getUserByEmail(String email) {
        Optional<UserEntity> foundUser = userDao.findByEmail(email);
        if(!foundUser.isPresent()) {
            throw new UserNotFoundException("User Not Found");
        }
        return entityDtoConverter.convertUserEntityToUserDto(foundUser.get());
    }
    @Override
    public UserDto getUserById(String userId) {
        Optional<UserEntity> foundUser = userDao.findById(userId);
        if(!foundUser.isPresent()) {
            throw new UserNotFoundException("User Not Found");
        }
        return entityDtoConverter.convertUserEntityToUserDto(foundUser.get());
    }

    @Override
    public List<UserAllDto> getAllUsers() {
        return  entityDtoConverter.toUserAllDtoList(userDao.findAll());
    }
}
