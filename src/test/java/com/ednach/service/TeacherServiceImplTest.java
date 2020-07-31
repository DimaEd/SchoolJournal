package com.ednach.service;

import com.ednach.component.LocalizedMessageSource;
import com.ednach.model.Teacher;
import com.ednach.model.User;
import com.ednach.repository.TeacherRepository;
import com.ednach.service.impl.TeacherServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest {
    @InjectMocks
    private TeacherServiceImpl teacherService;

    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private UserService userService;
    @Mock
    private LocalizedMessageSource localizedMessageSource;


    @Test
    void findAll() {
        List<Teacher> teacherList = Collections.singletonList(new Teacher());
        when(teacherRepository.findAll()).thenReturn(teacherList);
        assertEquals(teacherService.findAll(), teacherList);

    }

    @Test
    void findById() {
        final Teacher teacher = new Teacher();
        teacher.setId(1L);
        when(teacherRepository.findById(any(Long.class))).thenReturn(Optional.of(teacher));
        assertEquals(teacherService.findById(1L), teacher);
    }

    @Test
    void save() {
        final Teacher teacher = new Teacher();
        final User user = new User();
        user.setId(1L);
        teacher.setUser(user);
        when(teacherRepository.saveAndFlush(teacher)).thenReturn(teacher);
        when(userService.findById(any(Long.class))).thenReturn(user);
        assertEquals(teacherService.save(teacher), teacher);
    }

    @Test
    void update() {
        final Teacher teacher = new Teacher();
        teacher.setId(1L);
        final User user = new User();
        user.setId(1L);
        teacher.setUser(user);
        when(teacherRepository.findById(any(Long.class))).thenReturn(Optional.of(teacher));
        when(teacherRepository.saveAndFlush(teacher)).thenReturn(teacher);
        when(userService.findById(any(Long.class))).thenReturn(user);
        assertEquals(teacherService.update(teacher), teacher);
    }

    @Test
    void delete() {
        final Teacher teacher = new Teacher();
        teacher.setId(1L);
        doNothing().when(teacherRepository).delete(teacher);
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        assertDoesNotThrow(() -> teacherService.delete(teacher));

    }

    @Test
    void deleteById() {
        final Teacher teacher = new Teacher();
        teacher.setId(1L);
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        doNothing().when(teacherRepository).deleteById(any(Long.class));
        assertDoesNotThrow(() -> teacherService.deleteById(1L));
    }
}