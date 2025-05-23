package com.TrackMyItem.service.Impl.secure;

import com.TrackMyItem.dao.secure.AllUsersDao;
import com.TrackMyItem.dto.secure.AllUsersDto;
import com.TrackMyItem.dto.secure.JWTAuthResponse;
import com.TrackMyItem.dto.secure.SignIn;
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

    @Override
    public JWTAuthResponse signIn(SignIn signIn) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword()));
        var userByEmail = allUsersDao.findByEmail(signIn.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var generateToken = jwtUtils.generateToken(userByEmail.getEmail(), userByEmail.getAuthorities());
        return JWTAuthResponse.builder().token(generateToken).build();
    }

    @Override
    public JWTAuthResponse signUp(AllUsersDto allUsersDto) {
        allUsersDto.setUserId(utilData.generateAllUsersId());
        allUsersDto.setPassword(passwordEncoder.encode(allUsersDto.getPassword()));
        var savedUser = allUsersDao.save(entityDtoConvert.toUserEntity(allUsersDto));
        var generateToken = jwtUtils.generateToken(savedUser.getEmail(), savedUser.getAuthorities());
        return JWTAuthResponse.builder().token(generateToken).build();
    }
}
