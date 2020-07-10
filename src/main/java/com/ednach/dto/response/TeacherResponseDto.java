package com.ednach.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Response DTO (Data transfer object) class for Teacher entity.
 */
@Getter
@Setter
public class TeacherResponseDto {

    private Long id;

    private UserResponseDto user;

}
