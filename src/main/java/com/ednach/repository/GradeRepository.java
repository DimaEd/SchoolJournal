package com.ednach.repository;

import com.ednach.model.Grade;
import com.ednach.model.Schoolboy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * GradeRepository provides the necessary search operations
 */

public interface GradeRepository extends JpaRepository<Grade, Long> {

    boolean existsBySubject(String subject);

    Grade findBySubject(String subject);

    List<Grade> findBySchoolboy(Schoolboy b);

}
