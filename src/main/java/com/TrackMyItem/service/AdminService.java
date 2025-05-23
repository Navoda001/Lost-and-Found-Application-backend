package com.TrackMyItem.service;

import com.TrackMyItem.dto.AdminDto;
import com.TrackMyItem.dto.StaffDto;

public interface AdminService {
    void addAdmin(AdminDto adminDto);
    void updateAdmin(AdminDto adminDto);
    void deleteAdmin(String email);
    AdminDto getAdminById(String adminId);
    AdminDto getAdminByEmail(String email);
}