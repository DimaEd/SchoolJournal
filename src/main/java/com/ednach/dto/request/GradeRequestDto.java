package com.ednach.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Request DTO (Data transfer object) class for Grade entity.
 */
@Getter
@Setter
public class GradeRequestDto {

    private Long id;

    @NotNull(message = "{grade.schoolboyId.notNull}")
    private Long schoolboyId;

    @NotNull(message = "{grade.disciplineId.notNull}")
    private Long disciplineId;

    @NotNull(message = "{grade.mark.notNull}")
    private Long mark;

    @NotNull(message = "{grade.teacherId.notNull}")
    private Long teacherId;

    @NotNull(message = "{grade.date.notNull}")
    @NotEmpty(message = "{grade.date.notEmpty}")
    @Size(min = 3, max = 50, message = "{grade.date.size}")
    private String date;


}
