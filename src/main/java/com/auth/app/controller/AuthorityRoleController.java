package com.auth.app.controller;

import com.auth.app.service.AuthorityRoleService;
import com.auth.app.service.dto.RoleResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Slf4j
public class AuthorityRoleController {

    private final AuthorityRoleService authorityRoleService;

    @GetMapping("/roles")
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles() {
        log.info("REST request for get all roles");
        return ResponseEntity.ok(authorityRoleService.getAllRole());
    }
}
