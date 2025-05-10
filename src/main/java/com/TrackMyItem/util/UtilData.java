package com.TrackMyItem.util;

import com.TrackMyItem.dao.ItemDao;
import com.TrackMyItem.dao.RequestDao;
import com.TrackMyItem.dao.UserDao;
import com.TrackMyItem.entity.ItemEntity;
import com.TrackMyItem.entity.RequestEntity;
import com.TrackMyItem.entity.UserEntity;
import jakarta.annotation.PostConstruct;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

public class UtilData {
        private static ItemDao itemDao;
        private static RequestDao requestDao;
        private static UserDao userDao;

        //ItemId
    public static String generateItemId() {
        // Get the last inserted Item ordered by ID descending
        ItemEntity lastItem = itemDao.findTopByOrderByItemIdDesc();

        if (lastItem == null) {
            return "I001"; // Start with I001 if no doctors exist
        }

        // Extract numeric part of the ID and increment
        String lastId = lastItem.getItemId();
        int lastNumber = Integer.parseInt(lastId.substring(1));

        // Format with leading zeros to ensure 3 digits
        return String.format("I%03d", lastNumber + 1);
    }

    //RequestId
    public static String generateRequestId() {
        // Get the last inserted Request ordered by ID descending
        RequestEntity lastRequest = requestDao.findTopByOrderByRequestIdDesc();

        if (lastRequest == null) {
            return "R001"; // Start with R001 if no doctors exist
        }

        // Extract numeric part of the ID and increment
        String lastId = lastRequest.getRequestId();
        int lastNumber = Integer.parseInt(lastId.substring(1));

        // Format with leading zeros to ensure 3 digits
        return String.format("R%03d", lastNumber + 1);
    }

    //UserId
    public static String generateUserId() {
        // Get the last inserted Request ordered by ID descending
        UserEntity lastUser = userDao.findTopByOrderByUserIdDesc();

        if (lastUser == null) {
            return "U001"; // Start with U001 if no doctors exist
        }

        // Extract numeric part of the ID and increment
        String lastId = lastUser.getUserId();
        int lastNumber = Integer.parseInt(lastId.substring(1));

        // Format with leading zeros to ensure 3 digits
        return String.format("U%03d", lastNumber + 1);
    }

    //Generate last updated date
    public static LocalDate generateTodayDate(){
        return LocalDate.now();
    }

}
