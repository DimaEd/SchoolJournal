package com.ednach.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Request DTO (Data transfer object) class for Discipline entity.
 */
@Getter
@Setter
public class DisciplineRequestDto {

    private Long id;

    @Column(name = "nameSubject", unique = true, nullable = false)
    @NotNull(message = "{discipline.nameSubject.notNull}")
    @NotEmpty(message = "{discipline.nameSubject.notEmpty}")
    private String nameSubject;

    @NotNull(message = "{discipline.teacher.notNull}")
    private Long teacherId;
}
