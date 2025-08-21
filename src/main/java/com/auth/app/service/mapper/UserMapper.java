package com.auth.app.service.mapper;

import com.auth.app.entity.AuthorityRole;
import com.auth.app.entity.User;
import com.auth.app.service.dto.RegisterUserDTO;
import com.auth.app.service.dto.UserDetailsDTO;
import com.auth.app.service.mapper.config.BaseMapperConfig;
import com.auth.app.service.mapper.config.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(config = BaseMapperConfig.class, uses = {AuthorityRoleMapper.class})
public interface UserMapper extends GenericMapper<User, UserDetailsDTO> {
    @Override
    User toEntity(UserDetailsDTO dto);

    @Override
    UserDetailsDTO toDto(User entity);

    @Mapping(target = "roles", source = "roleId", qualifiedByName = "mapRole")
    @Mapping(target = "username", source = "email")
    @Mapping(target = "password", ignore = true)
    User toEntity(RegisterUserDTO dto);

    @Override
    List<UserDetailsDTO> toDto(List<User> entities);

    @Override
    List<User> toEntity(List<UserDetailsDTO> dtos);

    @Named("mapRole")
    default List<AuthorityRole> mapRole(List<Long> roleIds) {
        if (roleIds == null) return null;
        return roleIds.stream().map(roleId -> AuthorityRole.builder().id(roleId).build()).toList();
    }
}
