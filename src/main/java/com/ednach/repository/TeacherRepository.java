package com.ednach.repository;

import com.ednach.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TeacherRepository provides the necessary search operations
 */
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    boolean existsById(Long id);

    Teacher findTeacherById(Long id);
}
