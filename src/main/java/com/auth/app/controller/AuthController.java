package com.auth.app.controller;

import com.auth.app.entity.User;
import com.auth.app.exception.GlobalException;
import com.auth.app.exception.constant.ConstantApplicationCode;
import com.auth.app.exception.constant.ConstantMessage;
import com.auth.app.security.service.CustomUserDetails;
import com.auth.app.security.service.JwtService;
import com.auth.app.service.UserService;
import com.auth.app.service.dto.AuthResponseDTO;
import com.auth.app.service.dto.LoginDTO;
import com.auth.app.service.dto.RefreshTokenDTO;
import com.auth.app.service.dto.RegisterUserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

    private final JwtService jwtService;

    private final AuthenticationProvider authenticationProvider;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> registerUser(@RequestBody RegisterUserDTO registerUserDTO) {
        log.info("REST request for register user : {} ", registerUserDTO);
        User user = userService.registerUser(registerUserDTO);
        return ResponseEntity.ok(AuthResponseDTO.builder()
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUser(@RequestBody LoginDTO loginDTO) {
        log.info("REST request for login : {} ", loginDTO);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        Authentication authenticate = authenticationProvider.authenticate(authenticationToken);
        if (authenticate.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticationToken); // optional
            User user = userService.findByUsername(loginDTO.getUsername());
            return ResponseEntity.ok(AuthResponseDTO.builder()
                    .accessToken(jwtService.generateAccessToken(user))
                    .refreshToken(jwtService.generateRefreshToken(user))
                    .build());
        } else
            throw new GlobalException(ConstantMessage.INVALID_CREDENTIAL_MESSAGE, ConstantApplicationCode.INVALID_CREDENTIAL_APPLICATION_CODE, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO) {
        log.info("REST request for refresh token : {}", refreshTokenDTO);
        String username = jwtService.extractUsername(refreshTokenDTO.getRefreshToken());
        User user = userService.findByUsername(username);
        if (jwtService.validateToken(refreshTokenDTO.getRefreshToken(), new CustomUserDetails(user))) {
            return ResponseEntity.ok(AuthResponseDTO.builder()
                    .accessToken(jwtService.generateAccessToken(user))
                    .refreshToken(refreshTokenDTO.getRefreshToken())
                    .build());
        } else throw new GlobalException(ConstantMessage.PLEASE_TRY_AGAIN_MESSAGE, ConstantApplicationCode.PLEASE_TRY_AGAIN_APPLICATION_CODE, HttpStatus.BAD_REQUEST);
    }
}
