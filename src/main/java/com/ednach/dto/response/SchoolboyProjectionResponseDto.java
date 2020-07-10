package com.ednach.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolboyProjectionResponseDto {

    private Long id;

    private UserResponseDto user;

    private ClassroomProjectionResponseDto classroom;
}