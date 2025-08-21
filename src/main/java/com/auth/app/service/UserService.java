package com.auth.app.service;

import com.auth.app.entity.User;
import com.auth.app.service.dto.RegisterUserDTO;
import com.auth.app.service.dto.UserDetailsDTO;

import java.util.List;

public interface UserService {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    User findByUsername(String username);

    User registerUser(RegisterUserDTO registerUserDTO);

    boolean existsByPhoneNumber(Long phoneNumber);

    UserDetailsDTO getCurrentLoginUser();

    List<UserDetailsDTO> getAllUser();
}
