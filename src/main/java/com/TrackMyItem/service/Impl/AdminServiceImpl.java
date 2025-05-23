package com.TrackMyItem.service.Impl;

import com.TrackMyItem.dao.AdminDao;
import com.TrackMyItem.dao.secure.AllUsersDao;
import com.TrackMyItem.dto.AdminDto;
import com.TrackMyItem.dto.Role;
import com.TrackMyItem.entity.AdminEntity;
import com.TrackMyItem.entity.StaffEntity;
import com.TrackMyItem.entity.secure.AllUsersEntity;
import com.TrackMyItem.exception.UserNotFoundException;
import com.TrackMyItem.service.AdminService;
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
public class AdminServiceImpl implements AdminService {
    private final UtilData utilData;
    private final PasswordEncoder passwordEncoder;
    private final AllUsersDao allUsersDao;
    private final AdminDao adminDao;
    private final EntityDtoConverter entityDtoConverter;

    @Override
    public void addAdmin(AdminDto adminDto) {
        AllUsersEntity allUsersEntity = new AllUsersEntity();
        allUsersEntity.setUserId(utilData.generateAllUsersId());
        allUsersEntity.setEmail(adminDto.getEmail());
        allUsersEntity.setRole(Role.ADMIN);
        allUsersEntity.setPassword(passwordEncoder.encode("1234"));
        allUsersDao.save(allUsersEntity);

        adminDto.setAdminId(utilData.generateAdminId());
        adminDto.setUpdatedAt(utilData.generateTodayDate());
        adminDto.setRole(Role.ADMIN);
        adminDao.save(entityDtoConverter.toAdminEntity(adminDto));
    }

    @Override
    public void updateAdmin(AdminDto adminDto) {
        Optional<AdminEntity> foundUser = adminDao.findByEmail(adminDto.getEmail());
        if(!foundUser.isPresent()) {
            throw new UserNotFoundException("User Not Found");
        }
        foundUser.get().setFirstName(adminDto.getFirstName());
        foundUser.get().setLastName(adminDto.getLastName());

        foundUser.get().setUpdatedAt(utilData.generateTodayDate());
    }

    @Override
    public void deleteAdmin(String email) {
        Optional<AdminEntity> foundUser = adminDao.findByEmail(email);
        if(!foundUser.isPresent()) {
            throw new UserNotFoundException("User Not Found");
        }
        Optional<AllUsersEntity> foundedUser = allUsersDao.findByEmail(email);
        if(!foundedUser.isPresent()) {
            throw new UserNotFoundException("User Not Found");
        }
        allUsersDao.delete(foundedUser.get());
        adminDao.delete(foundUser.get());
    }

    @Override
    public AdminDto getAdminById(String adminId) {
        Optional<AdminEntity> foundUser = adminDao.findById(adminId);
        if(!foundUser.isPresent()) {
            throw new UserNotFoundException("User Not Found");
        }
        return entityDtoConverter.toAdminDto(foundUser.get());
    }

    @Override
    public AdminDto getAdminByEmail(String email) {
        Optional<AdminEntity> foundUser = adminDao.findByEmail(email);
        if(!foundUser.isPresent()) {
            throw new UserNotFoundException("User Not Found");
        }
        return entityDtoConverter.toAdminDto(foundUser.get());
    }
}
