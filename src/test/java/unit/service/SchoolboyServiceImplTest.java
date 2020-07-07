package unit.service;

import com.ednach.component.LocalizedMessageSource;
import com.ednach.model.Classroom;
import com.ednach.model.Schedule;
import com.ednach.model.Schoolboy;
import com.ednach.model.User;
import com.ednach.repository.SchoolboyRepository;
import com.ednach.repository.projection.SchoolboyProjection;
import com.ednach.service.ClassroomService;
import com.ednach.service.UserService;
import com.ednach.service.impl.ScheduleServiceImpl;
import com.ednach.service.impl.SchoolboyServiceImpl;
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
class SchoolboyServiceImplTest {

    @InjectMocks
    private SchoolboyServiceImpl schoolboyService;

    @Mock
    private SchoolboyRepository schoolboyRepository;
    @Mock
    private UserService userService;
    @Mock
    private ClassroomService classroomService;
    @Mock
    private LocalizedMessageSource localizedMessageSource;

    @Test
    void findAll() {
        List<SchoolboyProjection> scheduleList = Collections.singletonList(new SchoolboyProjection() {
            @Override
            public Long getSchoolboyId() {
                return null;
            }

            @Override
            public Long getUserId() {
                return null;
            }

            @Override
            public String getUserFirstName() {
                return null;
            }

            @Override
            public String getUserLastName() {
                return null;
            }

            @Override
            public Long getClassroomId() {
                return null;
            }

            @Override
            public String getClassName() {
                return null;
            }
        });
        when(schoolboyRepository.findAllCustom()).thenReturn(scheduleList);
        assertEquals(schoolboyService.findAll(), scheduleList);
    }

    @Test
    void findById() {
        final Schoolboy schoolboy = new Schoolboy();
        schoolboy.setId(1L);
        when(schoolboyRepository.findById(any(Long.class))).thenReturn(Optional.of(schoolboy));
        assertEquals(schoolboyService.findById(1L), schoolboy);

    }

    @Test
    void save() {
        final Schoolboy schoolboy = new Schoolboy();
        final User user = new User();
        user.setId(1L);
        final Classroom classroom = new Classroom();
        classroom.setId(1L);
        schoolboy.setUser(user);
        schoolboy.setClassroom(classroom);
        when(schoolboyRepository.saveAndFlush(schoolboy)).thenReturn(schoolboy);
        when(userService.findById(1L)).thenReturn(user);
        when(classroomService.findById(1L)).thenReturn(classroom);
        assertEquals(schoolboyService.save(schoolboy), schoolboy);
    }

    @Test
    void update() {
        final Schoolboy schoolboy = new Schoolboy();
        schoolboy.setId(1L);
        final User user = new User();
        user.setId(1L);
        final Classroom classroom = new Classroom();
        classroom.setId(1L);
        schoolboy.setUser(user);
        schoolboy.setClassroom(classroom);
        when(schoolboyRepository.findById(any(Long.class))).thenReturn(Optional.of(schoolboy));
        when(schoolboyRepository.saveAndFlush(schoolboy)).thenReturn(schoolboy);
        when(userService.findById(any(Long.class))).thenReturn(user);
        when(classroomService.findById(1L)).thenReturn(classroom);
        assertEquals(schoolboyService.update(schoolboy), schoolboy);

    }

    @Test
    void delete() {
        final Schoolboy schoolboy = new Schoolboy();
        schoolboy.setId(1L);
        when(schoolboyRepository.findById(any(Long.class))).thenReturn(Optional.of(schoolboy));
        doNothing().when(schoolboyRepository).delete(schoolboy);
        assertDoesNotThrow(() -> schoolboyService.delete(schoolboy));
    }

    @Test
    void deleteById() {
        final Schoolboy schoolboy = new Schoolboy();
        schoolboy.setId(1L);
        when(schoolboyRepository.findById(any(Long.class))).thenReturn(Optional.of(schoolboy));
        doNothing().when(schoolboyRepository).deleteById(any(Long.class));
        assertDoesNotThrow(() -> schoolboyService.deleteById(1L));
    }
}