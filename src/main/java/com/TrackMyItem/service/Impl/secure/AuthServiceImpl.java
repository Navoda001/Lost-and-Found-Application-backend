package com.TrackMyItem.service.Impl.secure;

import com.TrackMyItem.dao.AdminDao;
import com.TrackMyItem.dao.StaffDao;
import com.TrackMyItem.dao.UserDao;
import com.TrackMyItem.dao.secure.AllUsersDao;
import com.TrackMyItem.dto.Role;
import com.TrackMyItem.dto.UserDto;
import com.TrackMyItem.dto.secure.AllUsersDto;
import com.TrackMyItem.dto.secure.JWTAuthResponse;
import com.TrackMyItem.dto.secure.PasswordDto;
import com.TrackMyItem.dto.secure.SignIn;
import com.TrackMyItem.entity.UserEntity;
import com.TrackMyItem.entity.secure.AllUsersEntity;
import com.TrackMyItem.exception.PasswordUnmatchException;
import com.TrackMyItem.exception.UserNotFoundException;
import com.TrackMyItem.security.jwt.JWTUtils;
import com.TrackMyItem.service.secure.AuthService;
import com.TrackMyItem.util.EntityDtoConverter;
import com.TrackMyItem.util.UtilData;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AllUsersDao allUsersDao;
    private final JWTUtils jwtUtils;
    private final EntityDtoConverter entityDtoConvert;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UtilData utilData;
    private final UserDao userDao;
    private final AdminDao adminDao;
    private final StaffDao staffDao;

    @Override
    public JWTAuthResponse signIn(SignIn signIn) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword()));
        var userByEmail = allUsersDao.findByEmail(signIn.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Role role = userByEmail.getRole();

        if(role == Role.ADMIN){
            var foundUser = adminDao.findByEmail(signIn.getEmail());
            if(foundUser.isPresent()){
                foundUser.get().setLastLogin(utilData.generateTodayDate());
            }
        }else if(role == Role.USER){
            var foundUser = userDao.findByEmail(signIn.getEmail());
            if(foundUser.isPresent()){
                foundUser.get().setLastLogin(utilData.generateTodayDate());
            }
        }else if(role == Role.STAFF){
            var foundUser = staffDao.findByEmail(signIn.getEmail());
            if(foundUser.isPresent()){
                foundUser.get().setLastLogin(utilData.generateTodayDate());
            }
        }

        var generateToken = jwtUtils.generateToken(userByEmail.getEmail(), userByEmail.getAuthorities());
        return JWTAuthResponse.builder().token(generateToken).build();
    }

    @Override
    public JWTAuthResponse signUp(AllUsersDto allUsersDto) {
        allUsersDto.setUserId(utilData.generateAllUsersId());
        allUsersDto.setPassword(passwordEncoder.encode(allUsersDto.getPassword()));
        var savedUser = allUsersDao.save(entityDtoConvert.toUserEntity(allUsersDto));

        UserDto userDto = new UserDto();
        userDto.setUserId(utilData.generateUserId());
        userDto.setFirstName(allUsersDto.getFirstName());
        userDto.setLastName(allUsersDto.getLastName());
        userDto.setEmail(allUsersDto.getEmail());
        userDto.setPhoneNumber(allUsersDto.getPhoneNumber());
        userDto.setJoinDate(utilData.generateTodayDate());
        userDto.setLastLogin(utilData.generateTodayDate());
        userDto.setUpdatedAt(utilData.generateTodayDate());
        userDto.setRole(Role.USER);

        userDao.save(entityDtoConvert.convertUserDtoToUserEntity(userDto));

        var generateToken = jwtUtils.generateToken(savedUser.getEmail(), savedUser.getAuthorities());

        // add to userTable
        return JWTAuthResponse.builder().token(generateToken).build();
    }

    public void updateUserPassword(PasswordDto dto) {
        Optional<AllUsersEntity> foundedUser = allUsersDao.findByEmail(dto.getEmail());
        if(!foundedUser.isPresent()) {
            throw new UserNotFoundException("User Not Found");
        }
        Role role = foundedUser.get().getRole();
        var current = foundedUser.get().getPassword();

        if (!passwordEncoder.matches(dto.getCurrentPassword(), current)) {
            throw new PasswordUnmatchException("Current Password Not Matched");
        }



        if(role == Role.ADMIN){
            var foundUserEntity = adminDao.findByEmail(dto.getEmail());
            if(foundUserEntity.isPresent()){
                foundUserEntity.get().setUpdatedAt(utilData.generateTodayDate());
            }
        }else if(role == Role.USER){
            var foundUserEntity = userDao.findByEmail(dto.getEmail());
            if(foundUserEntity.isPresent()){
                foundUserEntity.get().setUpdatedAt(utilData.generateTodayDate());
            }
        }else if(role == Role.STAFF){
            var foundUserEntity = staffDao.findByEmail(dto.getEmail());
            if(foundUserEntity.isPresent()){
                foundUserEntity.get().setUpdatedAt(utilData.generateTodayDate());
            }
        }
        foundedUser.get().setPassword(passwordEncoder.encode(dto.getNewPassword()));
    }

}
