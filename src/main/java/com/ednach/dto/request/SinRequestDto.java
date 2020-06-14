package com.ednach.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Request DTO (Data transfer object) class for Sin entity.
 */
@Getter
@Setter
public class SinRequestDto {

    private Long id;

    @NotNull(message = "{sin.typeSin.notNull}")
    @NotEmpty(message = "{sin.typeSin.notEmpty}")
    @Size(min = 3, max = 50, message = "{sin.typeSin.size}")
    private String typeSin;

    @NotNull(message = "{sin.points.notNull}")
    private Long points;

    @NotNull(message = "{sin.teacherId.notNull}")
    private Long teacherId;

    @NotNull(message = "{sin.schoolboyId.notNull}")
    private Long schoolboyId;
}
