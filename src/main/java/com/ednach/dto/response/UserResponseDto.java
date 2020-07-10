package com.ednach.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Response DTO (Data transfer object) class for User entity.
 */
@Getter
@Setter
public class UserResponseDto {

    private Long id;

    private String firstName;

    private String lastName;
}
