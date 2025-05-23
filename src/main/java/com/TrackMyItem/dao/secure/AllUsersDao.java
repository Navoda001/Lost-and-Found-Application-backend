package com.TrackMyItem.dao.secure;


import com.TrackMyItem.entity.secure.AllUsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AllUsersDao extends JpaRepository<AllUsersEntity,String> {
    Optional<AllUsersEntity> findByEmail(String email);
}
