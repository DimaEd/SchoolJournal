package unit.service;

import com.ednach.component.LocalizedMessageSource;
import com.ednach.model.Role;
import com.ednach.repository.RoleRepository;
import com.ednach.service.impl.RoleServiceImpl;
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
class RoleServiceImplTest {
    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private LocalizedMessageSource localizedMessageSource;

    @Mock
    private RoleRepository roleRepository;

    @Test
    void findAll() {
        List<Role> roleList = Collections.singletonList(new Role());
        when(roleRepository.findAll()).thenReturn(roleList);
        assertEquals(roleService.findAll(), roleList);
    }

    @Test
    void findById() {
        final Role role = new Role();
        role.setId(1L);
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        assertEquals(roleService.findById(1L), role);
    }

    @Test
    void save() {
        final Role role = new Role();
        when(roleRepository.saveAndFlush(role)).thenReturn(role);
        assertEquals(roleService.save(role), role);
    }

    @Test
    void update() {
        final Role role = new Role();
        role.setId(1L);
        when(roleRepository.saveAndFlush(role)).thenReturn(role);
        assertEquals(roleService.update(role), role);
    }

    @Test
    void delete() {
        final Role role = new Role();
        role.setId(1L);
        when(roleRepository.findById(any(Long.class))).thenReturn(Optional.of(role));
        doNothing().when(roleRepository).delete(role);
        assertDoesNotThrow(() -> roleService.delete(role));
    }

    @Test
    void deleteById() {
        final Role role = new Role();
        role.setId(1L);
        when(roleRepository.findById(any(Long.class))).thenReturn(Optional.of(role));
        doNothing().when(roleRepository).deleteById(any(Long.class));
        assertDoesNotThrow(() -> roleService.deleteById(1L));
    }
}