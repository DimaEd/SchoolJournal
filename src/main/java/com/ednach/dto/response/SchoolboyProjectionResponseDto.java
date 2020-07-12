package com.ednach.dto.response;

import lombok.Getter;
import lombok.Setter;


/**
 * Response DTO (Data transfer object) class for SchoolboyProjection entity.
 */
@Getter
@Setter
public class SchoolboyProjectionResponseDto {

    private Long id;

    private UserResponseDto user;

    private ClassroomProjectionResponseDto classroom;
}