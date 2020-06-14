package com.ednach.service;

import com.ednach.model.Grade;
import com.ednach.model.Schoolboy;

import java.util.List;

/**
 * Interface with CRUD methods for Grade entity
 */
public interface GradeService {

    List<Grade> findAll();

    Grade findById(Long id);

    List<Grade> findBySchoolboy(Schoolboy b);

    Grade save(Grade grade);

    Grade update(Grade grade);

    void deleteById(Long id);

    void delete(Grade grade);
}