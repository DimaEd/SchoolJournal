package com.ednach.repository;

import com.ednach.model.Schoolboy;
import com.ednach.model.Sin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * SinRepository provides the necessary search operations
 */

public interface SinRepository extends JpaRepository<Sin, Long> {

    @Query("SELECT DISTINCT s, sch.classroom FROM Sin s JOIN FETCH s.schoolboy sch")
    List<Sin> findAll();

    boolean existsByTypeSin(String typeSin);

    Sin findByTypeSin(String typeSin);

    List<Sin> findBySchoolboy(Schoolboy schoolboy);
}
