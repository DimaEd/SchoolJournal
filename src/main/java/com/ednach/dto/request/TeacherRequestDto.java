package com.ednach.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Request DTO (Data transfer object) class for Teacher entity.
 */
@Getter
@Setter
public class TeacherRequestDto {

    private Long id;

    @NotNull(message = "{teacher.user.notNull}")
    private Long userId;
}
