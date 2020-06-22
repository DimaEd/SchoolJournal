package unit.service;

import com.ednach.component.LocalizedMessageSource;
import com.ednach.model.Role;
import com.ednach.model.User;
import com.ednach.repository.UserRepository;
import com.ednach.service.RoleService;
import com.ednach.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleService roleService;
    @Mock
    private LocalizedMessageSource localizedMessageSource;

    @Test
    void findAll() {
        List<User> userList = Collections.singletonList(new User());
        when(userRepository.findAll()).thenReturn(userList);
        assertEquals(userService.findAll(),userList);
    }

    @Test
    void findById() {
        final User user = new User();
        user.setId(1L);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        assertEquals(userService.findById(1L),user);
    }

    @Test
    void findByFirstName() {
        final User user = new User();
        user.setId(1L);
        user.setFirstName("Ednach");
        when(userRepository.findByFirstName(any(String.class))).thenReturn(user);
        assertEquals(userService.findByFirstName("Ednach"),user);
    }

    @Test
    void save() {
        final User user = new User();
        final Set<Role> roleSet = getRoles();
        user.setRoles(roleSet);
        when(userRepository.saveAndFlush(user)).thenReturn(user);
        when(roleService.findById(1L)).thenReturn(roleSet.iterator().next());
        assertEquals(userService.save(user), user);
    }

    @Test
    void update() {
        final User user = new User();
        user.setId(1L);
        final Set<Role> roleSet = getRoles();
        user.setRoles(roleSet);
        when(userRepository.saveAndFlush(user)).thenReturn(user);
        when(roleService.findById(1L)).thenReturn(roleSet.iterator().next());
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        assertEquals(userService.update(user), user);
    }

    @Test
    void delete() {
        final User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);
        assertDoesNotThrow(() -> userService.delete(user));
    }

    @Test
    void deleteById() {
        final User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(any(Long.class));
        assertDoesNotThrow(() -> userService.deleteById(1L));
    }
    private Set<Role> getRoles() {
        return Stream.of(1L).map(roleId -> {
            Role role = new Role();
            role.setId(roleId);
            role.setName("user");
            return role;
        }).collect(Collectors.toSet());
    }
}