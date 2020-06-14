package com.ednach.service;

import com.ednach.model.Role;
import com.ednach.model.User;

import java.util.List;

/**
 * Interface with CRUD methods for User entity
 */
public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User findByFirstName(String firstName);

    List<User> findByRoles(Role role);

    User save(User user);

    User update(User user);

    void delete(User user);

    void deleteById(Long id);
}
