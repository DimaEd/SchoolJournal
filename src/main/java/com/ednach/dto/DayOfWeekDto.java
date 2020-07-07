package com.ednach.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO (Data transfer object) class for DayOfWeek entity.
 * Both for response and request
 */
@Setter
@Getter
public class DayOfWeekDto {

    private Long id;

    @NotNull(message = "{DayOfWeekDto.dayOfWeek.notNull}")
    @NotEmpty(message = "{DayOfWeekDto.dayOfWeek.notEmpty}")
    @Size(min = 3, max = 50, message = "{DayOfWeekDto.dayOfWeek.size}")
    private String day;
}
