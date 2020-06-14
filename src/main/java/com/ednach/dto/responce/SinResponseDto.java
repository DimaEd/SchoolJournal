package com.ednach.dto.responce;

import lombok.Getter;
import lombok.Setter;

/**
 * Response DTO (Data transfer object) class for Sin entity.
 */
@Getter
@Setter
public class SinResponseDto {

    private Long id;

    private String typeSin;

    private Long points;

    private SchoolboyResponseDto schoolboy;

    private TeacherResponseDto teacher;
}
