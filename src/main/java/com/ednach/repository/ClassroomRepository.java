package com.ednach.repository;

import com.ednach.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * ClassroomRepository provides the necessary search operations
 */

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    boolean existsByClassName(String classroom);

    Classroom findByClassName(String classroom);

    Optional<Classroom> findById(Long id);

}
