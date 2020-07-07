package com.ednach.repository;

import com.ednach.model.Schoolboy;
import com.ednach.model.User;
import com.ednach.repository.projection.SchoolboyProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * ScheduleRepository provides the necessary search operations
 */
public interface SchoolboyRepository extends JpaRepository<Schoolboy, Long> {

    @Query("SELECT DISTINCT s.id as schoolboyId, s.user.id as userId, s.user.firstName as userFirstName, s.user.lastName as userLastName, s.classroom.id as classroomId, s.classroom.className as className FROM Schoolboy s")
    List<SchoolboyProjection> findAllCustom();

    Schoolboy findByUser(User user);

    boolean existsById(Long id);

    boolean existsByUser(User user);
}
