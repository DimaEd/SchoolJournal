package com.ednach.service;

import com.ednach.model.Discipline;

import java.util.List;

/**
 * Interface with CRUD methods for Discipline entity
 */
public interface DisciplineService {

    List<Discipline> findAll();

    Discipline findById(Long id);

    Discipline save(Discipline discipline);

    Discipline update(Discipline discipline);

    void delete(Discipline discipline);

    void deleteById(Long id);


}
