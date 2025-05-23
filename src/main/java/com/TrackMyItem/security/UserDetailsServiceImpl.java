package com.TrackMyItem.security;

import com.TrackMyItem.dao.secure.AllUsersDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AllUsersDao allUsersDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return allUsersDao.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
