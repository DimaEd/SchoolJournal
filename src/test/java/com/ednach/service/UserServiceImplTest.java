package com.ednach.service;

import com.ednach.component.LocalizedMessageSource;
import com.ednach.model.Role;
import com.ednach.model.User;
import com.ednach.repository.UserRepository;
import com.ednach.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void findAll() {
        List<User> userList = Collections.singletonList(new User());
        when(userRepository.findAll()).thenReturn(userList);
        assertEquals(userService.findAll(), userList);
    }

    @Test
    void findByFirstName() {
        final User user = new User();
        user.setFirstName("Moroz");
        List<User> userList = Collections.singletonList(user);
        when(userRepository.findByFirstName(any(String.class))).thenReturn(userList);
        assertEquals(userService.findByFirstName("Moroz"), userList);
    }

    @Test
    void findById() {
        final User user = new User();
        user.setId(1L);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        assertEquals(userService.findById(1L), user);
    }

    @Test
    void findByEmail() {
        final User user = new User();
        user.setId(1L);
        user.setEmail("moroz@mail.ru");
        when(userRepository.findByEmail(any(String.class))).thenReturn(user);
        assertEquals(userService.findByEmail("moroz@mail.ru"), user);
    }

    @Test
    void save() {
        final User user = new User();
        final Role role = new Role();
        role.setId(1L);
        final Set<Role> roles = Collections.singleton(role);
        user.setRoles(roles);
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(user);
        when(roleService.findById(anyLong())).thenReturn(role);
        assertEquals(user, userService.save(user));
    }

    @Test
    void update() {
        final User user = new User();
        user.setId(1L);
        final Set<Role> roleSet = getRoles();
        user.setRoles(roleSet);
        when(roleService.findById(1L)).thenReturn(roleSet.iterator().next());
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(userRepository.saveAndFlush(user)).thenReturn(user);
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