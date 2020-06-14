package com.ednach.dto.responce;

import lombok.Getter;
import lombok.Setter;

/**
 * Response DTO (Data transfer object) class for Grade entity.
 */
@Getter
@Setter
public class GradeResponseDto {

    private Long id;

    private String subject;

    private String mark;

    private TeacherResponseDto teacher;

    private SchoolboyResponseDto schoolboy;

}
