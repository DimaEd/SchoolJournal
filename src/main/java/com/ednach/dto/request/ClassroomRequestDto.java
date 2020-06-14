package com.ednach.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Request DTO (Data transfer object) class for Classroom entity.
 */
@Getter
@Setter
public class ClassroomRequestDto {

    private Long id;

    @NotNull(message = "{classroom.className.notNull}")
    @NotEmpty(message = "{classroom.className.notEmpty}")
    private String className;

    @NotNull(message = "{classroom.teacherId.notNull}")
    private Long teacherId;
}
