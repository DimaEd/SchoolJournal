package unit.service;

import com.ednach.component.LocalizedMessageSource;
import com.ednach.model.Classroom;
import com.ednach.model.DayOfWeek;
import com.ednach.model.Discipline;
import com.ednach.model.Schedule;
import com.ednach.repository.ScheduleRepository;
import com.ednach.service.ClassroomService;
import com.ednach.service.DayOfWeekService;
import com.ednach.service.DisciplineService;
import com.ednach.service.impl.ScheduleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceImplTest {

    @InjectMocks
    private ScheduleServiceImpl scheduleService;
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private ClassroomService classroomService;
    @Mock
    private DisciplineService disciplineService;
    @Mock
    private DayOfWeekService dayOfWeekService;

    @Mock
    private LocalizedMessageSource localizedMessageSource;

    @Test
    void findAll() {
        List<Schedule> scheduleList = Collections.singletonList(new Schedule());
        when(scheduleRepository.findAll()).thenReturn(scheduleList);
        assertEquals(scheduleService.findAll(),scheduleList);
    }

    @Test
    void findById() {
        final Schedule schedule = new Schedule();
        schedule.setId(1L);
        when(scheduleRepository.findById(any(Long.class))).thenReturn(Optional.of(schedule));
        assertEquals(scheduleService.findById(1L),schedule);
    }

    @Test
    void save() {
        final Schedule schedule = new Schedule();
        final Discipline discipline = new Discipline();
        discipline.setId(1L);
        final DayOfWeek dayOfWeek = new DayOfWeek();
        dayOfWeek.setId(1l);
        final Classroom classroom = new Classroom();
        classroom.setId(1L);
        schedule.setDiscipline(discipline);
        schedule.setDayOfWeek(dayOfWeek);
        schedule.setClassroom(classroom);
        when(scheduleRepository.saveAndFlush(schedule)).thenReturn(schedule);
        when(disciplineService.findById(any(Long.class))).thenReturn(discipline);
        when(dayOfWeekService.findById(any(Long.class))).thenReturn(dayOfWeek);
        when(classroomService.findById(any(Long.class))).thenReturn(classroom);
        assertEquals(scheduleService.save(schedule),schedule);
    }

    @Test
    void update() {
        final Schedule schedule = new Schedule();
        schedule.setId(1L);
        final Discipline discipline = new Discipline();
        discipline.setId(1L);
        final DayOfWeek dayOfWeek = new DayOfWeek();
        dayOfWeek.setId(1l);
        final Classroom classroom = new Classroom();
        classroom.setId(1L);
        schedule.setDiscipline(discipline);
        schedule.setDayOfWeek(dayOfWeek);
        schedule.setClassroom(classroom);
        when(scheduleRepository.findById(any(Long.class))).thenReturn(Optional.of(schedule));
        when(scheduleRepository.saveAndFlush(schedule)).thenReturn(schedule);
        when(disciplineService.findById(any(Long.class))).thenReturn(discipline);
        when(dayOfWeekService.findById(any(Long.class))).thenReturn(dayOfWeek);
        when(classroomService.findById(any(Long.class))).thenReturn(classroom);
        assertEquals(scheduleService.update(schedule),schedule);

    }

    @Test
    void delete() {
        final Schedule schedule = new Schedule();
        schedule.setId(1L);
        when(scheduleRepository.findById(any(Long.class))).thenReturn(Optional.of(schedule));
        doNothing().when(scheduleRepository).delete(schedule);
        assertDoesNotThrow(() -> scheduleService.delete(schedule));
    }

    @Test
    void deleteById() {
        final Schedule schedule = new Schedule();
        schedule.setId(1L);
        when(scheduleRepository.findById(any(Long.class))).thenReturn(Optional.of(schedule));
        doNothing().when(scheduleRepository).deleteById(any(Long.class));
        assertDoesNotThrow(() -> scheduleService.deleteById(1L));
    }
}