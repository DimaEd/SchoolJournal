package unit.service;

import com.ednach.component.LocalizedMessageSource;
import com.ednach.model.Grade;
import com.ednach.model.Schoolboy;
import com.ednach.model.Teacher;
import com.ednach.repository.GradeRepository;
import com.ednach.service.SchoolboyService;
import com.ednach.service.TeacherService;
import com.ednach.service.impl.GradeServiceImpl;
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
class GradeServiceImplTest {
    @InjectMocks
    private GradeServiceImpl gradeService;

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private TeacherService teacherService;

    @Mock
    private SchoolboyService schoolboyService;

    @Mock
    private LocalizedMessageSource localizedMessageSource;

    @Test
    void findAll() {
        final List<Grade> gradeList = Collections.singletonList(new Grade());
        when(gradeRepository.findAll()).thenReturn(gradeList);
        assertEquals(gradeService.findAll(), gradeList);
    }

    @Test
    void findById() {
        final Grade grade = new Grade();
        when(gradeRepository.findById(any(Long.class))).thenReturn(Optional.of(grade));
        assertEquals(gradeService.findById(1L),grade);
    }

    @Test
    void findBySchoolboy() {
        List<Grade> gradeList = Collections.singletonList(new Grade());
        final Schoolboy schoolboy = new Schoolboy();
        schoolboy.setId(1L);
        when(gradeRepository.findBySchoolboy(schoolboy)).thenReturn(gradeList);
        assertEquals(gradeService.findBySchoolboy(schoolboy),gradeList);
    }

    @Test
    void save() {
        final Grade grade = new Grade();
        final Schoolboy schoolboy = new Schoolboy();
        schoolboy.setId(1L);
        final Teacher teacher = new Teacher();
        teacher.setId(1L);
        grade.setSchoolboy(schoolboy);
        grade.setTeacher(teacher);
        when(gradeRepository.saveAndFlush(grade)).thenReturn(grade);
        assertEquals(gradeService.save(grade),grade);
    }

    @Test
    void update() {
        final Grade grade = new Grade();
        grade.setId(1L);
        final Schoolboy schoolboy = new Schoolboy();
        schoolboy.setId(1L);
        final Teacher teacher = new Teacher();
        teacher.setId(1L);
        grade.setSchoolboy(schoolboy);
        grade.setTeacher(teacher);
        when(gradeRepository.saveAndFlush(grade)).thenReturn(grade);
        when(teacherService.findById(1L)).thenReturn(teacher);
        when(schoolboyService.findById(1L)).thenReturn(schoolboy);
        assertEquals(gradeService.update(grade),grade);
    }

    @Test
    void deleteById() {
        final Grade grade = new Grade();
        grade.setId(1L);
        when(gradeRepository.findById(any(Long.class))).thenReturn(Optional.of(grade));
        doNothing().when(gradeRepository).deleteById(any(Long.class));
        assertDoesNotThrow(() -> gradeService.deleteById(1L));
    }

    @Test
    void delete() {
        final Grade grade = new Grade();
        grade.setId(1L);
        when(gradeRepository.findById(any(Long.class))).thenReturn(Optional.of(grade));
        doNothing().when(gradeRepository).delete(grade);
        assertDoesNotThrow(() -> gradeService.delete(grade));
    }
}