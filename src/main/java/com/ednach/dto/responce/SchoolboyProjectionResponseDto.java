package com.ednach.dto.responce;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolboyProjectionResponseDto {

    private Long id;

    private UserResponseDto user;

    private ClassroomProjectionResponseDto classroom;
}