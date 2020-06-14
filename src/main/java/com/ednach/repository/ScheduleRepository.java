package com.ednach.repository;

import com.ednach.model.Classroom;
import com.ednach.model.DayOfWeek;
import com.ednach.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * ScheduleRepository provides the necessary search operations
 */
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    boolean existsById(Long id);

    Schedule findScheduleById(Long id);

    List<Schedule> findScheduleByClassroomAndDayOfWeek(Classroom classroom, DayOfWeek dayOfWeek);

}
