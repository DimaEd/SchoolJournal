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

    boolean existsByPassword(String password);

    boolean existsByFirstName(String firstName);

    boolean existsByLastName(String lastName);

    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%',:firstName))")
    List<User> findUserByFirstName(@Param("firstName") String firstName);

    User findByFirstName(String firstName);

    User findByPassword(String password);

}
