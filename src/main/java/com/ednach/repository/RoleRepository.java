package com.ednach.repository;

import com.ednach.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * RoleRepository provides the necessary search operations
 */

public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByName(String name);

    Role findByName(String name);

    Optional<Role> findById(Long id);
}
