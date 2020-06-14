package com.ednach.service;

import com.ednach.model.Classroom;

import java.util.List;

/**
 * Interface with CRUD methods for Classroom entity
 */
public interface ClassroomService {
    List<Classroom> findAll();

    Classroom findById(Long id);

    Classroom findByClassName(String classroom);

    Classroom save(Classroom classroom);

    Classroom update(Classroom classroom);

    void deleteById(Long id);

    void delete(Classroom classroom);
}
