package com.ednach.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Request DTO (Data transfer object) class for Schoolboy entity.
 */
@Getter
@Setter
public class SchoolboyRequestDto {

    private Long id;

    @NotNull(message = "{schoolboy.userId.notNull}")
    private Long userId;

    @NotNull(message = "{schoolboy.classroomId.notNull}")
    private Long classroomId;
}
