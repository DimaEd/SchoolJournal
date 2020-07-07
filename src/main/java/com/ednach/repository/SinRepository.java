package com.ednach.repository;

import com.ednach.model.Schoolboy;
import com.ednach.model.Sin;
import com.ednach.repository.projection.SinProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * SinRepository provides the necessary search operations
 */

public interface SinRepository extends JpaRepository<Sin, Long> {

    @Query("SELECT DISTINCT s.id as sinId, s.typeSin as typeSin, s.points as points,s.schoolboy.id as schoolboyId, s.schoolboy.user.id as userId, s.schoolboy.user.firstName as userFirstName, s.schoolboy.user.lastName as userLastName, s.schoolboy.classroom.id as classroomId, s.schoolboy.classroom.className as className, s.teacher.id as teacherId , s.teacher as teacher FROM Sin s")
    List<SinProjection> findAllCustom();

    @Query("SELECT s FROM Sin s WHERE LOWER(s.typeSin) LIKE LOWER(CONCAT('%',:typeSin))")
    List<Sin> findByTypeSin(@Param("typeSin") String typeSin);

    List<Sin> findBySchoolboy(Schoolboy schoolboy);
}
