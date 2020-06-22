package com.ednach.service.impl;

import com.ednach.repository.UserRepository;
import com.ednach.model.Role;
import com.ednach.model.User;
import com.ednach.service.RoleService;
import com.ednach.service.UserService;
import com.ednach.component.LocalizedMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of service interface for User entity
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final LocalizedMessageSource localizedMessageSource;
    private final RoleService roleService;
    private final UserRepository userRepository;

    public UserServiceImpl(LocalizedMessageSource localizedMessageSource, RoleService roleService, UserRepository userRepository) {
        this.localizedMessageSource = localizedMessageSource;
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.user.notExist", new Object[]{})));
    }

    @Override
    public User findByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    @Override
    public User save(User user) {
        validate(user.getId() != null, localizedMessageSource.getMessage("error.user.notHaveId", new Object[]{}));
        validate(userRepository.existsByFirstName(user.getFirstName())&&userRepository.existsByLastName(user.getLastName()),localizedMessageSource.getMessage("error.user.firstNameWithLastName.notUnique",new Object[]{}));
        return saveAndFlush(user);
    }

    @Override
    public User update(User user) {
        final Long id = user.getId();
        validate(id == null, localizedMessageSource.getMessage("error.user.haveId", new Object[]{}));
        final User duplicateUser = userRepository.findByFirstName(user.getFirstName());
        final boolean isDuplicateExists = duplicateUser != null && !Objects.equals(duplicateUser.getId(), id);
        validate(isDuplicateExists, localizedMessageSource.getMessage("error.user.firstName.notUnique", new Object[]{}));
        findById(id);
        return saveAndFlush(user);
    }

    @Override
    public void delete(User user) {
        final Long id = user.getId();
        validate(id == null, localizedMessageSource.getMessage("error.user.haveId", new Object[]{}));
        findById(id);
        userRepository.delete(user);
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }

    private User saveAndFlush(User user) {
        user.getRoles().forEach(role -> {
            validate(role == null || role.getId() == null, localizedMessageSource.getMessage("error.user.role.isNull", new Object[]{}));
            role.setName(roleService.findById(role.getId()).getName());
        });
        return userRepository.saveAndFlush(user);
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }

    }

}
