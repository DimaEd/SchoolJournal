package com.ednach.dto.response;

import lombok.Getter;
import lombok.Setter;


/**
 * Response DTO (Data transfer object) class for SinProjection entity.
 */
@Setter
@Getter
public class SinProjectionResponseDto {

    private Long id;

    private String typeSin;

    private Long points;

    private SchoolboyProjectionResponseDto schoolboy;

    private TeacherResponseDto teacher;
}
