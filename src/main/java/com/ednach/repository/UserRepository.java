package com.ednach.repository;

import com.ednach.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * UserRepository provides the necessary search operations
 */

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE  LOWER(u.firstName) LIKE LOWER(CONCAT('%',:firstName))")
    List<User> findByFirstName(@Param("firstName") String firstName);

    User findByEmail(String email);

    boolean existsByEmail(String email);

}
