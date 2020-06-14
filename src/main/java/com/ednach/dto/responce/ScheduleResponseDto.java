package com.ednach.dto.responce;

import com.ednach.dto.DayOfWeekDto;
import lombok.Getter;
import lombok.Setter;

/**
 * Response DTO (Data transfer object) class for Schedule entity.
 */
@Getter
@Setter
public class ScheduleResponseDto {

    private Long id;

    private ClassroomResponseDto classroom;

    private DisciplineResponseDto discipline;

    private DayOfWeekDto dayOfWeek;
}
