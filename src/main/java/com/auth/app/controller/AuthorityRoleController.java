package com.auth.app.controller;

import com.auth.app.service.AuthorityRoleService;
import com.auth.app.service.dto.RoleResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthorityRoleController {

    private final AuthorityRoleService authorityRoleService;

    @GetMapping("/roles")
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles(){
        return ResponseEntity.ok(authorityRoleService.getAllRole());
    }
}
