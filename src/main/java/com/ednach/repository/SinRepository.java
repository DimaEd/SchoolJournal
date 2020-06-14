package com.ednach.repository;

import com.ednach.model.Schoolboy;
import com.ednach.model.Sin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * SinRepository provides the necessary search operations
 */

public interface SinRepository extends JpaRepository<Sin, Long> {

    boolean existsByTypeSin(String typeSin);

    Sin findByTypeSin(String typeSin);

    List<Sin> findBySchoolboy(Schoolboy schoolboy);
}
