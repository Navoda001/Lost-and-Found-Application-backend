package com.TrackMyItem.service;

import com.TrackMyItem.dto.ItemDto;
import com.TrackMyItem.dto.StaffDto;
import com.TrackMyItem.dto.UserDto;

import java.util.List;

public interface StaffService {
    void addStaff(StaffDto staff);
    void updateStaff(StaffDto staff);
    void updateImage(StaffDto staff);
    void deleteStaff(String email);
    StaffDto getStaffById(String staffId);
    StaffDto getStaffByemail(String email);
}
