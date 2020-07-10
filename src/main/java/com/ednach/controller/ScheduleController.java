package com.ednach.controller;

import com.ednach.dto.request.ScheduleRequestDto;
import com.ednach.dto.response.ScheduleResponseDto;
import com.ednach.model.Classroom;
import com.ednach.model.DayOfWeek;
import com.ednach.model.Discipline;
import com.ednach.model.Schedule;
import com.ednach.service.ScheduleService;
import com.ednach.component.LocalizedMessageSource;
import com.ednach.service.ClassroomService;
import com.ednach.service.DayOfWeekService;
import lombok.RequiredArgsConstructor;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final Mapper mapper;
    private final LocalizedMessageSource localizedMessageSource;
    private final ScheduleService scheduleService;
    private final ClassroomService classroomService;
    private final DayOfWeekService dayOfWeekService;

    /**
     * Finds all schedule entities and maps them to DTO
     *
     * @return - ResponseEntity with the given body and status code
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ScheduleResponseDto>> getAll() {
        final List<Schedule> schedules = scheduleService.findAll();
        final List<ScheduleResponseDto> scheduleResponseDtoList = schedules.stream()
                .map((schedule) -> mapper.map(schedule, ScheduleResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(scheduleResponseDtoList, HttpStatus.OK);
    }

    /**
     * Finds all schedule entities and maps them to DTO
     *
     * @param className-classroom entity name
     * @param dayWeek-            dayOfWeek entity dayWeek
     * @return ResponseEntity with the given body and status code
     */
    @RequestMapping(value = "/class/{className},{dayWeek}", method = RequestMethod.GET)
    public ResponseEntity<List<ScheduleResponseDto>> getAllScheduleByDayAndClass(@PathVariable String className, @PathVariable String dayWeek) {
        Classroom classroom = classroomService.findByClassName(className);
        DayOfWeek day = dayOfWeekService.findByDayOfWeek(dayWeek);
        final List<Schedule> schedules = scheduleService.findScheduleByClassroomAndDayOfWeek(classroom, day);
        final List<ScheduleResponseDto> scheduleResponseDtoList = schedules.stream()
                .map((schedule) -> mapper.map(schedule, ScheduleResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(scheduleResponseDtoList, HttpStatus.OK);
    }

    /**
     * Finds schedule entity by id and maps it to DTO
     *
     * @param id - schedule entity id
     * @return - ResponseEntity with the given body and status code
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ScheduleResponseDto> getOne(@PathVariable Long id) {
        final ScheduleResponseDto scheduleResponseDto = mapper.map(scheduleService.findById(id), ScheduleResponseDto.class);
        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    /**
     * Saves schedule entity with transferred parameters and maps it to DTO
     *
     * @param scheduleRequestDto - the body of the web request
     * @return - ResponseEntity with the given body and status code
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ScheduleResponseDto> save(@Valid @RequestBody ScheduleRequestDto scheduleRequestDto) {
        scheduleRequestDto.setId(null);
        final ScheduleResponseDto scheduleResponseDto = mapper.map(scheduleService.save(getSchedule(scheduleRequestDto)), ScheduleResponseDto.class);
        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    /**
     * Updates schedule entity with transferred parameters by entities id and maps it to DTO
     *
     * @param scheduleRequestDto - the body of the web request
     * @param id                 - schedule entity id
     * @return - ResponseEntity with the given body and status code
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ScheduleResponseDto> update(@Valid @RequestBody ScheduleRequestDto scheduleRequestDto, @PathVariable Long id) {
        if (!Objects.equals(id, scheduleRequestDto.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.schedule.unexpectedId", new Object[]{}));
        }
        final ScheduleResponseDto scheduleResponseDto = mapper.map(scheduleService.update(getSchedule(scheduleRequestDto)), ScheduleResponseDto.class);
        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    /**
     * Deletes schedule entity by its id
     *
     * @param id - schedule entity id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        scheduleService.deleteById(id);
    }

    private Schedule getSchedule(ScheduleRequestDto scheduleRequestDto) {
        final Schedule schedule = mapper.map(scheduleRequestDto, Schedule.class);
        final Classroom classroom = new Classroom();
        classroom.setId(scheduleRequestDto.getClassroomId());
        final Discipline discipline = new Discipline();
        discipline.setId(scheduleRequestDto.getDisciplineId());
        final DayOfWeek dayOfWeek = new DayOfWeek();
        dayOfWeek.setId(scheduleRequestDto.getDayOfWeekId());
        discipline.setId(scheduleRequestDto.getDisciplineId());
        classroom.setId(scheduleRequestDto.getClassroomId());
        dayOfWeek.setId(scheduleRequestDto.getDayOfWeekId());

        schedule.setDayOfWeek(dayOfWeek);
        schedule.setClassroom(classroom);
        schedule.setDiscipline(discipline);

        return schedule;
    }
}
