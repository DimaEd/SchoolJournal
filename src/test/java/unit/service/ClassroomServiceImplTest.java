package unit.service;

import com.ednach.component.LocalizedMessageSource;
import com.ednach.model.Classroom;
import com.ednach.model.Teacher;
import com.ednach.repository.ClassroomRepository;
import com.ednach.service.TeacherService;
import com.ednach.service.impl.ClassroomServiceImpl;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClassroomServiceImplTest {

    @InjectMocks
    private ClassroomServiceImpl classroomService;

    @Mock
    private ClassroomRepository classroomRepository;

    @Mock
    private LocalizedMessageSource localizedMessageSource;

    @Mock
    private TeacherService teacherService;


    @Test
    void findAll() {
        final List<Classroom> classroomList = Collections.singletonList(new Classroom());
        when(classroomRepository.findAll()).thenReturn(classroomList);
        assertEquals(classroomService.findAll(), classroomList);
    }

    @Test
    void findById() {
        final Classroom classroom = new Classroom();
        when(classroomRepository.findById(any(Long.class))).thenReturn(Optional.of(classroom));
        assertEquals(classroomService.findById(1L), classroom);
    }

    @Test
    void findByClassName() {
        final Classroom classroom = new Classroom();
        classroom.setClassName("10A");
        when(classroomRepository.findByClassName(any(String.class))).thenReturn(classroom);
        assertEquals(classroomService.findByClassName("10A"), classroom);
    }

    @Test
    void save() {
        final Classroom classroom = new Classroom();
        final Teacher teacher = new Teacher(1L);
        classroom.setTeacher(teacher);
        when(classroomRepository.saveAndFlush(classroom)).thenReturn(classroom);
        when(teacherService.findById(1L)).thenReturn(teacher);
        assertEquals(classroomService.save(classroom), classroom);
    }

    @Test
    void update() {
        final Classroom classroom = new Classroom();
        classroom.setId(1L);
        final Teacher teacher = new Teacher(1L);
        classroom.setTeacher(teacher);
        when(classroomRepository.saveAndFlush(classroom)).thenReturn(classroom);
        when(teacherService.findById(1L)).thenReturn(teacher);
        assertEquals(classroomService.update(classroom), classroom);
    }

    @Test
    void deleteById() {
        final Classroom classroom = new Classroom();
        classroom.setId(1L);
        when(classroomRepository.findById(anyLong())).thenReturn(Optional.of(classroom));
        doNothing().when(classroomRepository).deleteById(anyLong());
        assertDoesNotThrow(() -> classroomService.deleteById(1L));
    }

    @Test
    void delete() {
        final Classroom classroom = new Classroom();
        classroom.setId(1L);
        when(classroomRepository.findById(anyLong())).thenReturn(Optional.of(classroom));
        doNothing().when(classroomRepository).delete(classroom);
        assertDoesNotThrow(() -> classroomService.delete(classroom));
    }
}