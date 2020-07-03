package com.ednach.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Request DTO (Data transfer object) class for Schedule entity.
 */
@Getter
@Setter
public class ScheduleRequestDto {

    private Long id;

    @NotNull(message = "{schedule.classroom.notNull}")
    private Long classroomId;

    @NotNull(message = "{schedule.discipline.notNull}")
    private Long disciplineId;

    @NotNull(message = "schedule.dayOfWeek.notNull")
    private Long dayOfWeekId;
}
