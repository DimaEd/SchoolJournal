package com.ednach.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SinProjectionResponseDto {

    private Long id;

    private String typeSin;

    private Long points;

    private SchoolboyProjectionResponseDto schoolboy;

    private TeacherResponseDto teacher;
}