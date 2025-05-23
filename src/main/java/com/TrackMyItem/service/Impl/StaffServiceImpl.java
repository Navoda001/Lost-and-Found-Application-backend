package com.TrackMyItem.service.Impl;

import com.TrackMyItem.dao.StaffDao;
import com.TrackMyItem.dao.secure.AllUsersDao;
import com.TrackMyItem.dto.Role;
import com.TrackMyItem.dto.StaffDto;
import com.TrackMyItem.dto.UserDto;
import com.TrackMyItem.dto.secure.AllUsersDto;
import com.TrackMyItem.entity.StaffEntity;
import com.TrackMyItem.entity.UserEntity;
import com.TrackMyItem.entity.secure.AllUsersEntity;
import com.TrackMyItem.exception.UserNotFoundException;
import com.TrackMyItem.service.StaffService;
import com.TrackMyItem.util.EntityDtoConverter;
import com.TrackMyItem.util.UtilData;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {
    private final UtilData utilData;
    private final PasswordEncoder passwordEncoder;
    private final StaffDao staffDao;
    private final AllUsersDao allUsersDao;
    private final EntityDtoConverter entityDtoConverter;

    @Override
    public void addStaff(StaffDto staffDto) {
        AllUsersEntity allUsersEntity = new AllUsersEntity();
        allUsersEntity.setUserId(utilData.generateAllUsersId());
        allUsersEntity.setEmail(staffDto.getEmail());
        allUsersEntity.setRole(Role.STAFF);
        allUsersEntity.setPassword(passwordEncoder.encode("1234"));
        allUsersDao.save(allUsersEntity);

        staffDto.setStaffId(utilData.generateStaffId());
        staffDto.setCreatedAt(utilData.generateTodayDate());
        staffDto.setUpdatedAt(utilData.generateTodayDate());
        staffDao.save(entityDtoConverter.toStaffEntity(staffDto));

    }

    @Override
    public void updateImage(StaffDto staffDto) {
        Optional<StaffEntity> foundUser = staffDao.findByEmail(staffDto.getEmail());
        foundUser.get().setImage(staffDto.getImage());
        foundUser.get().setUpdatedAt(utilData.generateTodayDate());
    }

    @Override
    public void updateStaff(StaffDto staffDto) {
        Optional<StaffEntity> foundUser = staffDao.findByEmail(staffDto.getEmail());
        if(!foundUser.isPresent()) {
            throw new UserNotFoundException("User Not Found");
        }
        foundUser.get().setFirstName(staffDto.getFirstName());
        foundUser.get().setLastName(staffDto.getLastName());

        foundUser.get().setUpdatedAt(utilData.generateTodayDate());
    }

    @Override
    public void deleteStaff(String email) {
        Optional<StaffEntity> foundUser = staffDao.findByEmail(email);
        if(!foundUser.isPresent()) {
            throw new UserNotFoundException("User Not Found");
        }
        Optional<AllUsersEntity> foundedUser = allUsersDao.findByEmail(email);
        if(!foundedUser.isPresent()) {
            throw new UserNotFoundException("User Not Found");
        }
        allUsersDao.delete(foundedUser.get());
        staffDao.delete(foundUser.get());
    }

    @Override
    public StaffDto getStaffById(String staffId) {
        Optional<StaffEntity> foundUser = staffDao.findById(staffId);
        if(!foundUser.isPresent()) {
            throw new UserNotFoundException("User Not Found");
        }
        return entityDtoConverter.toStaffDto(foundUser.get());
    }

    @Override
    public StaffDto getStaffByemail(String email) {
        Optional<StaffEntity> foundUser = staffDao.findByEmail(email);
        if(!foundUser.isPresent()) {
            throw new UserNotFoundException("User Not Found");
        }
        return entityDtoConverter.toStaffDto(foundUser.get());
    }

}
