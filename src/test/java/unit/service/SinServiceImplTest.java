package unit.service;

import com.ednach.component.LocalizedMessageSource;
import com.ednach.model.Schoolboy;
import com.ednach.model.Sin;
import com.ednach.model.Teacher;
import com.ednach.repository.SinRepository;
import com.ednach.service.SchoolboyService;
import com.ednach.service.TeacherService;
import com.ednach.service.impl.SinServiceImpl;
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
class SinServiceImplTest {
    @InjectMocks
    private SinServiceImpl sinService;

    @Mock
    private SinRepository sinRepository;
    @Mock
    private LocalizedMessageSource localizedMessageSource;
    @Mock
    private SchoolboyService schoolboyService;
    @Mock
    private TeacherService teacherService;

    @Test
    void findByTypeSin() {
        final Sin sin = new Sin();
        sin.setTypeSin("fight");
        when(sinRepository.findByTypeSin(any(String.class))).thenReturn(sin);
        assertEquals(sinService.findByTypeSin("fight"),sin);
    }

    @Test
    void findBySchoolboy() {
        final Sin sin = new Sin();
        List<Sin> sinList = Collections.singletonList(sin);
        final Schoolboy schoolboy = new Schoolboy();
        sin.setSchoolboy(schoolboy);
        when(sinRepository.findBySchoolboy(any(Schoolboy.class))).thenReturn(sinList);
        assertEquals(sinService.findBySchoolboy(schoolboy),sinList);
    }

    @Test
    void findAll() {
        List<Sin> sinList = Collections.singletonList(new Sin());
        when(sinRepository.findAll()).thenReturn(sinList);
        assertEquals(sinService.findAll(),sinList);
    }

    @Test
    void findById() {
        final Sin sin = new Sin();
        sin.setId(1L);
        when(sinRepository.findById(any(Long.class))).thenReturn(Optional.of(sin));
        assertEquals(sinService.findById(1L),sin);
    }

    @Test
    void save() {
        final Sin sin = new Sin();
        final Schoolboy schoolboy = new Schoolboy();
        schoolboy.setId(1L);
        final Teacher teacher = new Teacher();
        teacher.setId(1L);
        sin.setSchoolboy(schoolboy);
        sin.setTeacher(teacher);
        when(sinRepository.saveAndFlush(sin)).thenReturn(sin);
        when(schoolboyService.findById(any(Long.class))).thenReturn(schoolboy);
        when(teacherService.findById(1L)).thenReturn(teacher);
        assertEquals(sinService.save(sin),sin);


    }

    @Test
    void update() {
        final Sin sin = new Sin();
        sin.setId(1L);
        final Schoolboy schoolboy = new Schoolboy();
        schoolboy.setId(1L);
        final Teacher teacher = new Teacher();
        teacher.setId(1L);
        sin.setSchoolboy(schoolboy);
        sin.setTeacher(teacher);
        when(sinRepository.saveAndFlush(sin)).thenReturn(sin);
        when(schoolboyService.findById(any(Long.class))).thenReturn(schoolboy);
        when(teacherService.findById(1L)).thenReturn(teacher);
        assertEquals(sinService.update(sin),sin);
    }

    @Test
    void delete() {
        final Sin sin = new Sin();
        sin.setId(1L);
        when(sinRepository.findById(1L)).thenReturn(Optional.of(sin));
        doNothing().when(sinRepository).delete(sin);
        assertDoesNotThrow(() -> sinService.delete(sin));
    }

    @Test
    void deleteById() {
        final Sin sin = new Sin();
        sin.setId(1L);
        when(sinRepository.findById(1L)).thenReturn(Optional.of(sin));
        doNothing().when(sinRepository).deleteById(any(Long.class));
        assertDoesNotThrow(() -> sinService.deleteById(1L));
    }
}