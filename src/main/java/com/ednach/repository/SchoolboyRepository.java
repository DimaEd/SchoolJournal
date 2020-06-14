package com.ednach.repository;

import com.ednach.model.Schoolboy;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ScheduleRepository provides the necessary search operations
 */
public interface SchoolboyRepository extends JpaRepository<Schoolboy, Long> {

    boolean existsById(Long id);

    Schoolboy findSchoolboyById(Long id);
}
