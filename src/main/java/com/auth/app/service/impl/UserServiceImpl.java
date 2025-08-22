package com.auth.app.service.impl;

import com.auth.app.entity.User;
import com.auth.app.exception.GlobalException;
import com.auth.app.exception.constant.ConstantApplicationCode;
import com.auth.app.exception.constant.ConstantMessage;
import com.auth.app.repository.UserRepository;
import com.auth.app.service.UserService;
import com.auth.app.service.dto.RegisterUserDTO;
import com.auth.app.service.dto.UserDetailsDTO;
import com.auth.app.service.mapper.UserMapper;
import com.auth.app.utility.UserUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    @Override
    public boolean existsByEmail(String email) {
        log.info("Request for check email : {} exists", email);
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        log.info("Request for check username : {} exists", username);
        return userRepository.existsByUsername(username);
    }

    @Override
    public User findByUsername(String username) {
        log.info("Request for get User by Username : {}", username);
        return userRepository.findByUsername(username).orElseThrow(() -> new GlobalException(ConstantMessage.USER_NOT_FOUND_MESSAGE, ConstantApplicationCode.USER_NOT_FOUND_APPLICATION_CODE, HttpStatus.BAD_REQUEST));
    }

    @Override
    public User registerUser(RegisterUserDTO registerUserDTO) {
        log.info("Request for register user : {} ", registerUserDTO);
        if (existsByEmail(registerUserDTO.getEmail())) {
            log.warn("Email {} Already exist", registerUserDTO.getEmail());
            throw new GlobalException(ConstantMessage.EMAIL_ALREADY_EXIST_MESSAGE, ConstantApplicationCode.EMAIL_ALREADY_EXIST_APPLICATION_CODE, HttpStatus.BAD_REQUEST);
        }
        if (existsByPhoneNumber(registerUserDTO.getPhoneNumber())) {
            log.warn("Phone number {} Already exist", registerUserDTO.getPhoneNumber());
            throw new GlobalException(ConstantMessage.PHONE_NUMBER_ALREADY_EXIST_MESSAGE, ConstantApplicationCode.PHONE_NUMBER_ALREADY_EXIST_APPLICATION_CODE, HttpStatus.BAD_REQUEST);
        }
        User user = userMapper.toEntity(registerUserDTO);
        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        user.setStatus(true);
        return userRepository.save(user);
    }

    @Override
    public boolean existsByPhoneNumber(Long phoneNumber) {
        log.info("Request for check Phone number : {} exists", phoneNumber);
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public UserDetailsDTO getCurrentLoginUser() {
        log.info("Request for get Current login user");
        return userMapper.toDto(UserUtility.getCurrentLoginUser());
    }

    @Override
    public List<UserDetailsDTO> getAllUser() {
        log.info("Request for get all user");
        return userRepository.findAllByUsernameIsNot(UserUtility.getCurrentLoginUsername())
                .stream()
                .map(userMapper::toDto)
                .toList();
    }
}
