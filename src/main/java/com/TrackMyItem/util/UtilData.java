package com.TrackMyItem.util;

import com.TrackMyItem.dao.*;
import com.TrackMyItem.dao.secure.AllUsersDao;
import com.TrackMyItem.entity.*;
import com.TrackMyItem.entity.secure.AllUsersEntity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class UtilData {
        private final ItemDao itemDao;
        private final RequestDao requestDao;
        private final UserDao userDao;
        private final AllUsersDao allUsersDao;
        private final StaffDao staffDao;
        private final AdminDao adminDao;

        //ItemId
    public  String generateItemId() {
        // Get the last inserted Item ordered by ID descending
        ItemEntity lastItem = itemDao.findTopByOrderByItemIdDesc();

        if (lastItem == null) {
            return "I001"; // Start with I001 if no items exist
        }

        // Extract numeric part of the ID and increment
        String lastId = lastItem.getItemId();
        int lastNumber = Integer.parseInt(lastId.substring(1));

        // Format with leading zeros to ensure 3 digits
        return String.format("I%03d", lastNumber + 1);
    }

    //RequestId
    public  String generateRequestId() {
        // Get the last inserted Request ordered by ID descending
        RequestEntity lastRequest = requestDao.findTopByOrderByRequestIdDesc();

        if (lastRequest == null) {
            return "R001"; // Start with R001 if no requests exist
        }

        // Extract numeric part of the ID and increment
        String lastId = lastRequest.getRequestId();
        int lastNumber = Integer.parseInt(lastId.substring(1));

        // Format with leading zeros to ensure 3 digits
        return String.format("R%03d", lastNumber + 1);
    }

    //UserId
    public  String generateUserId() {
        // Get the last inserted Request ordered by ID descending
        UserEntity lastUser = userDao.findTopByOrderByUserIdDesc();

        if (lastUser == null) {
            return "U001"; // Start with U001 if no users exist
        }

        // Extract numeric part of the ID and increment
        String lastId = lastUser.getUserId();
        int lastNumber = Integer.parseInt(lastId.substring(1));

        // Format with leading zeros to ensure 3 digits
        return String.format("U%03d", lastNumber + 1);
    }
        //AllUserId
    public  String generateAllUsersId() {
        // Get the last inserted Request ordered by ID descending
        AllUsersEntity lastUser = allUsersDao.findTopByOrderByUserIdDesc();

        if (lastUser == null) {
            return "AU001"; // Start with AU001 if no AllUsers exist
        }

        // Extract numeric part of the ID and increment
        String lastId = lastUser.getUserId();
        int lastNumber = Integer.parseInt(lastId.substring(1));

        // Format with leading zeros to ensure 3 digits
        return String.format("AU%03d", lastNumber + 1);
    }
        //staff Id
    public  String generateStaffId() {
        // Get the last inserted Request ordered by ID descending
        StaffEntity lastUser = staffDao.findTopByOrderByStaffIdDesc();

        if (lastUser == null) {
            return "S001"; // Start with S001 if no Staff exist
        }

        // Extract numeric part of the ID and increment
        String lastId = lastUser.getStaffId();
        int lastNumber = Integer.parseInt(lastId.substring(1));

        // Format with leading zeros to ensure 3 digits
        return String.format("S%03d", lastNumber + 1);
    }

    //admin Id
    public  String generateAdminId() {
        // Get the last inserted Request ordered by ID descending
        AdminEntity lastUser = adminDao.findTopByOrderByAdminIdDesc();

        if (lastUser == null) {
            return "S001"; // Start with S001 if no Admin exist
        }

        // Extract numeric part of the ID and increment
        String lastId = lastUser.getAdminId();
        int lastNumber = Integer.parseInt(lastId.substring(1));

        // Format with leading zeros to ensure 3 digits
        return String.format("A%03d", lastNumber + 1);
    }

    //Generate last updated date
    public  LocalDate generateTodayDate(){
        return LocalDate.now();
    }

}
