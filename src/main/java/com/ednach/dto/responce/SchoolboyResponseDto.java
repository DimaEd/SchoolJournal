package com.ednach.dto.responce;

import lombok.Getter;
import lombok.Setter;

/**
 * Response DTO (Data transfer object) class for Schoolboy entity.
 */
@Getter
@Setter
public class SchoolboyResponseDto {

    private Long id;

    private UserResponseDto user;

    private ClassroomResponseDto classroom;
}
