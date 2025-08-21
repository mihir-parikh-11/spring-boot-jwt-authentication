package com.auth.app.service.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserDetailsDTO {

    private Long id;

    private String username;

    private String email;

    private Long phoneNumber;

    private String firstName;

    private String lastName;

    private boolean status;

    private List<RoleResponseDTO> roles;
}

