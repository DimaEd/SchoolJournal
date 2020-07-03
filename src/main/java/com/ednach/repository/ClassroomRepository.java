package com.ednach.repository;

import com.ednach.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * ClassroomRepository provides the necessary search operations
 */

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    boolean existsByClassName(String classroom);

    @Query("SELECT DISTINCT c FROM  Classroom c JOIN c.teacher t")
    List<Classroom> findAll();

    Classroom findByClassName(String classroom);

    Optional<Classroom> findById(Long id);

}
