package com.ednach.dto.responce;

import com.ednach.dto.RoleDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Response DTO (Data transfer object) class for User entity.
 */
@Getter
@Setter
public class UserResponseDto {

    private Long id;

    private String firstName;

    private String lastName;

    private Set<RoleDto> roles;
}
