package com.TrackMyItem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAllDto implements Serializable {

    private String userId;
    private String firstName;
    private String lastName;

}
