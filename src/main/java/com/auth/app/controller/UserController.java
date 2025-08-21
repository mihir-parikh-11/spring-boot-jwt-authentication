package com.auth.app.controller;

import com.auth.app.service.UserService;
import com.auth.app.service.dto.UserDetailsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/profile")
    public ResponseEntity<UserDetailsDTO> getUserDetails() {
        return ResponseEntity.ok(userService.getCurrentLoginUser());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<UserDetailsDTO>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }
}
