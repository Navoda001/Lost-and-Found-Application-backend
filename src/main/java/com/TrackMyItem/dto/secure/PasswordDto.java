package com.TrackMyItem.dto.secure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PasswordDto implements Serializable {
    private String email;
    private String newPassword;
    private String currentPassword;
}
