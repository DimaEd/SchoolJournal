package com.ednach.repository;

import com.ednach.model.Role;
import com.ednach.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * UserRepository provides the necessary search operations
 */

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByPassword(String password);

    boolean existsByFirstName(String firstName);

    @Query("SELECT DISTINCT u FROM User u JOIN FETCH u.roles")
    User findByFirstName(String firstName);

    @Query("SELECT DISTINCT u FROM User u JOIN FETCH u.roles ORDER BY u.id")
    List<User> findAllWithRoles();

    @Query("SELECT DISTINCT u FROM User u JOIN FETCH u.roles WHERE u.id=:id ORDER BY u.id")
    Optional<User> findByIdWithRoles(@Param("id") Long id);

    List<User> findUserByRoles(Role roles);

    User findByPassword(String password);

}
