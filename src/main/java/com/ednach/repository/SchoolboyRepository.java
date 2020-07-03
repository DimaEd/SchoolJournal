package com.ednach.repository;

import com.ednach.model.Schoolboy;
import com.ednach.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * ScheduleRepository provides the necessary search operations
 */
public interface SchoolboyRepository extends JpaRepository<Schoolboy, Long> {

   @Query("SELECT DISTINCT s, cl.className FROM Schoolboy s JOIN fetch s.classroom cl where s.id = cl.id ")
    List<Schoolboy> findAll();

    Schoolboy findByUser(User user);

    boolean existsById(Long id);

    boolean existsByUser(User user);
}
