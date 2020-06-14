package com.ednach.service;

import com.ednach.model.Classroom;
import com.ednach.model.DayOfWeek;
import com.ednach.model.Schedule;

import java.util.List;

/**
 * Interface with CRUD methods for Schedule entity
 */
public interface ScheduleService {

    List<Schedule> findAll();

    List<Schedule> findScheduleByClassroomAndDayOfWeek(Classroom classroom, DayOfWeek dayOfWeek);

    Schedule findById(Long id);

    Schedule save(Schedule schedule);

    Schedule update(Schedule schedule);

    void delete(Schedule schedule);

    void deleteById(Long id);

}
