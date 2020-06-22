package com.ednach.repository;

import com.ednach.model.Schoolboy;
import com.ednach.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ScheduleRepository provides the necessary search operations
 */
public interface SchoolboyRepository extends JpaRepository<Schoolboy, Long> {

    Schoolboy findByUser(User user);

    boolean existsById(Long id);

    boolean existsByUser(User user);
}
