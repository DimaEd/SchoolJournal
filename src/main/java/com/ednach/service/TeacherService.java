package com.ednach.service;

import com.ednach.model.Teacher;

import java.util.List;

/**
 * Interface with CRUD methods for Teacher entity
 */
public interface TeacherService {

    List<Teacher> findAll();

    Teacher findById(Long id);

    Teacher save(Teacher teacher);

    Teacher update(Teacher teacher);

    void delete(Teacher teacher);

    void deleteById(Long id);
}
