package com.auth.app.service.mapper;

import com.auth.app.entity.AuthorityRole;
import com.auth.app.service.dto.RoleResponseDTO;
import com.auth.app.service.mapper.config.BaseMapperConfig;
import com.auth.app.service.mapper.config.GenericMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = BaseMapperConfig.class)
public interface AuthorityRoleMapper extends GenericMapper<AuthorityRole, RoleResponseDTO> {
    @Override
    AuthorityRole toEntity(RoleResponseDTO dto);

    @Override
    RoleResponseDTO toDto(AuthorityRole entity);

    @Override
    List<RoleResponseDTO> toDto(List<AuthorityRole> entities);

    @Override
    List<AuthorityRole> toEntity(List<RoleResponseDTO> dtos);
}
