package com.ednach.dto.responce;

import lombok.Getter;
import lombok.Setter;

/**
 * Response DTO (Data transfer object) class for Classroom entity.
 */
@Getter
@Setter
public class ClassroomResponseDto {

    private Long id;

    private String className;

    private TeacherResponseDto teacher;
}
