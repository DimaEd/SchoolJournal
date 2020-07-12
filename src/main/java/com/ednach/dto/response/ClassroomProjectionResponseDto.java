package com.ednach.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Response DTO (Data transfer object) class for ClassroomProjection entity.
 */
@Getter
@Setter
public class ClassroomProjectionResponseDto {

    private Long id;

    private String className;
}