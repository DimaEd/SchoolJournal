package com.ednach.service.impl;

import com.ednach.repository.UserRepository;
import com.ednach.model.User;
import com.ednach.service.RoleService;
import com.ednach.service.UserService;
import com.ednach.component.LocalizedMessageSource;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of service interface for User entity
 */
@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final LocalizedMessageSource localizedMessageSource;
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    /**
     * Finds all User entities
     *
     * @return - List of User entities
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Finds the User entity with the given id
     *
     * @param id - User entity id
     * @return - User entity
     */
    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.user.notExist", new Object[]{})));
    }

    /**
     * Finds all User entities
     *
     * @param firstName - User entity firstName
     * @return - List of User entities
     */
    @Override
    public List<User> findByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    /**
     * Finds the User entity with the given email
     *
     * @param email - User entity email
     * @return - User entity
     */
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Saves a given user entity
     *
     * @param user - user Entity
     * @return - the saved user entity
     */
    @Override
    public User save(User user) {
        validate(user.getId() != null, localizedMessageSource.getMessage("error.user.notHaveId", new Object[]{}));
        validate(userRepository.existsByEmail(user.getEmail()), localizedMessageSource.getMessage("error.user.email.notUnique", new Object[]{}));
        return saveAndFlush(user);
    }

    /**
     * Updates a user entity and flushes changes instantly
     *
     * @param user - user entity
     * @return - the updated user entity
     */
    @Override
    public User update(User user) {
        final Long id = user.getId();
        validate(id == null, localizedMessageSource.getMessage("error.user.haveId", new Object[]{}));
        final User duplicateUser = userRepository.findByEmail(user.getEmail());
        final boolean isDuplicateExists = duplicateUser != null && !Objects.equals(duplicateUser.getId(), id);
        validate(isDuplicateExists, localizedMessageSource.getMessage("error.user.email.notUnique", new Object[]{}));
        findById(id);
        return saveAndFlush(user);
    }

    /**
     * Deletes a given user entity
     *
     * @param user - user entity
     */
    @Override
    public void delete(User user) {
        final Long id = user.getId();
        validate(id == null, localizedMessageSource.getMessage("error.user.haveId", new Object[]{}));
        findById(id);
        userRepository.delete(user);
    }

    /**
     * Deletes the user entity with the given id
     *
     * @param id - user entity id
     */
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
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.saveAndFlush(user);
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }

    }

}
