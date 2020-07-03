package com.ednach.repository;

import com.ednach.model.Grade;
import com.ednach.model.Schoolboy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * GradeRepository provides the necessary search operations
 */

public interface GradeRepository extends JpaRepository<Grade, Long> {
     @Query("SELECT gr FROM Grade gr JOIN FETCH gr.discipline d where d.id = gr.id ")
    List<Grade> findAll();

    List<Grade> findBySchoolboy(Schoolboy b);

}
