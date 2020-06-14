package com.ednach.repository;

import com.ednach.model.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DisciplineRepository provides the necessary search operations
 */
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {

    boolean existsByNameSubject(String nameSubject);

    Discipline findByNameSubject(String nameSubject);
}
