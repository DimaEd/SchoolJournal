package com.ednach.service;


import com.ednach.model.Role;

import java.util.List;

/**
 * Interface with CRUD methods for Role entity
 */
public interface RoleService {

    List<Role> findAll();

    Role findById(Long id);

    Role save(Role role);


    Role update(Role role);


    void delete(Role role);


    void deleteById(Long id);

}
