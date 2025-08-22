package com.auth.app.service.impl;

import com.auth.app.repository.AuthorityRoleRepository;
import com.auth.app.service.AuthorityRoleService;
import com.auth.app.service.dto.RoleResponseDTO;
import com.auth.app.service.mapper.AuthorityRoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorityRoleServiceImpl implements AuthorityRoleService {
    private final AuthorityRoleRepository authorityRoleRepository;

    private final AuthorityRoleMapper authorityRoleMapper;

    @Override
    public List<RoleResponseDTO> getAllRole() {
        log.info("Request for get all roles");
        return authorityRoleMapper.toDto(authorityRoleRepository.findAll());
    }
}
