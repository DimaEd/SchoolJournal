package com.ednach.service.impl;

import com.ednach.component.LocalizedMessageSource;
import com.ednach.repository.ScheduleRepository;
import com.ednach.model.Classroom;
import com.ednach.model.DayOfWeek;
import com.ednach.model.Schedule;
import com.ednach.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of service interface for Schedule entity
 */
@RequiredArgsConstructor
@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    final ScheduleRepository scheduleRepository;
    final ClassroomService classroomService;
    final DisciplineService disciplineService;
    final DayOfWeekService dayOfWeekService;
    final LocalizedMessageSource localizedMessageSource;

    /**
     * Finds all Schedule entities
     *
     * @return - List of Schedule entities
     */
    @Override
    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    /**
     * Finds the Schedule entity with the given id
     *
     * @param id - Schedule entity id
     * @return - Schedule entity
     */
    @Override
    public Schedule findById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.schedule.notExist", new Object[]{})));
    }

    /**
     * Finds all Schedule entity with the given classroom and day
     *
     * @param classroom - Schedule entity classroom
     * @param dayOfWeek - Schedule entity day
     * @return - List of Schedule entities
     */
    @Override
    public List<Schedule> findScheduleByClassroomAndDayOfWeek(Classroom classroom, DayOfWeek dayOfWeek) {
        return scheduleRepository.findScheduleByClassroomAndDayOfWeek(classroom, dayOfWeek);
    }

    /**
     * Saves a given schedule entity
     *
     * @param schedule - schedule Entity
     * @return - the saved schedule entity
     */
    @Override
    public Schedule save(Schedule schedule) {
        validate(schedule.getId() != null, localizedMessageSource.getMessage("error.schedule.notHaveId", new Object[]{}));
        return saveAndFlush(schedule);
    }

    /**
     * Updates a schedule entity and flushes changes instantly
     *
     * @param schedule - schedule entity
     * @return - the updated schedule entity
     */
    @Override
    public Schedule update(Schedule schedule) {
        final Long id = schedule.getId();
        validate(id == null, localizedMessageSource.getMessage("error.schedule.haveId", new Object[]{}));
        findById(id);
        return saveAndFlush(schedule);
    }

    /**
     * Deletes a given schedule entity
     *
     * @param schedule - schedule entity
     */
    @Override
    public void delete(Schedule schedule) {
        final Long id = schedule.getId();
        validate(schedule.getId() == null, localizedMessageSource.getMessage("error.schedule.haveId", new Object[]{}));
        findById(id);
        scheduleRepository.delete(schedule);
    }

    /**
     * Deletes the schedule entity with the given id
     *
     * @param id - schedule entity id
     */
    @Override
    public void deleteById(Long id) {
        findById(id);
        scheduleRepository.deleteById(id);
    }

    private Schedule saveAndFlush(Schedule schedule) {
        validate(schedule.getClassroom() == null || schedule.getClassroom().getId() == null, localizedMessageSource.getMessage("error.schedule.classroom.isNull", new Object[]{}));
        validate(schedule.getDiscipline() == null || schedule.getDiscipline().getId() == null, localizedMessageSource.getMessage("error.schedule.discipline.isNull", new Object[]{}));
        validate(schedule.getDayOfWeek() == null || schedule.getDayOfWeek().getId() == null, localizedMessageSource.getMessage("error.schedule.dayOfWeek.isNull", new Object[]{}));

        schedule.setClassroom(classroomService.findById(schedule.getClassroom().getId()));
        schedule.setDiscipline(disciplineService.findById(schedule.getDiscipline().getId()));
        schedule.setDayOfWeek(dayOfWeekService.findById(schedule.getDayOfWeek().getId()));
        return scheduleRepository.saveAndFlush(schedule);
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }
}
