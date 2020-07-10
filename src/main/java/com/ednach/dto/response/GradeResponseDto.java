package com.ednach.dto.response;


import lombok.Getter;
import lombok.Setter;

/**
 * Response DTO (Data transfer object) class for Grade entity.
 */
@Getter
@Setter
public class GradeResponseDto {

    private Long id;

    private SchoolboyResponseDto schoolboy;

    private DisciplineResponseDto discipline;

    private Long mark;

    private TeacherResponseDto teacher;



}
