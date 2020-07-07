package unit.service;

import com.ednach.component.LocalizedMessageSource;
import com.ednach.model.Discipline;
import com.ednach.model.Teacher;
import com.ednach.repository.DisciplineRepository;
import com.ednach.service.TeacherService;
import com.ednach.service.impl.DisciplineServiceImpl;
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
class DisciplineServiceImplTest {

    @InjectMocks
    private DisciplineServiceImpl disciplineService;

    @Mock
    private DisciplineRepository disciplineRepository;

    @Mock
    private LocalizedMessageSource localizedMessageSource;

    @Mock
    private TeacherService teacherService;

    @Test
    void findAll() {
        List<Discipline> disciplineList = Collections.singletonList(new Discipline());
        when(disciplineRepository.findAll()).thenReturn(disciplineList);
        assertEquals(disciplineService.findAll(), disciplineList);
    }

    @Test
    void findById() {
        final Discipline discipline = new Discipline();
        when(disciplineRepository.findById(any(Long.class))).thenReturn(Optional.of(discipline));
        assertEquals(disciplineService.findById(any(Long.class)), discipline);
    }

    @Test
    void save() {
        final Discipline discipline = new Discipline();
        final Teacher teacher = new Teacher();
        teacher.setId(1L);
        discipline.setTeacher(teacher);
        when(disciplineRepository.saveAndFlush(discipline)).thenReturn(discipline);
        when(teacherService.findById(1l)).thenReturn(teacher);
        assertEquals(disciplineService.save(discipline), discipline);
    }

    @Test
    void update() {
        final Discipline discipline = new Discipline();
        discipline.setId(1L);
        final Teacher teacher = new Teacher();
        teacher.setId(1L);
        discipline.setTeacher(teacher);
        when(disciplineRepository.findById(any(Long.class))).thenReturn(Optional.of(discipline));
        when(disciplineRepository.saveAndFlush(discipline)).thenReturn(discipline);
        when(teacherService.findById(1l)).thenReturn(teacher);
        assertEquals(disciplineService.update(discipline), discipline);
    }

    @Test
    void delete() {
        final Discipline discipline = new Discipline();
        discipline.setId(1L);
        when(disciplineRepository.findById(any(Long.class))).thenReturn(Optional.of(discipline));
        doNothing().when(disciplineRepository).delete(discipline);
        assertDoesNotThrow(() -> disciplineService.delete(discipline));
    }

    @Test
    void deleteById() {
        final Discipline discipline = new Discipline();
        discipline.setId(1L);
        when(disciplineRepository.findById(any(Long.class))).thenReturn(Optional.of(discipline));
        doNothing().when(disciplineRepository).deleteById(any(Long.class));
        assertDoesNotThrow(() -> disciplineService.deleteById(1L));
    }
}