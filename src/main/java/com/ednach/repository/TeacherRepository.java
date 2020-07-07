package com.ednach.repository;

import com.ednach.model.Teacher;
import com.ednach.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TeacherRepository provides the necessary search operations
 */
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    boolean existsByUser(User user);

    boolean existsById(Long id);

    Teacher findByUser(User user);

}
