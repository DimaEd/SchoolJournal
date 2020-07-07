package com.ednach.service;

import com.ednach.model.Schoolboy;
import com.ednach.repository.projection.SchoolboyProjection;

import java.util.List;

/**
 * Interface with CRUD methods for Schoolboy entity
 */
public interface SchoolboyService {

    List<SchoolboyProjection> findAll();

    Schoolboy findById(Long id);

    Schoolboy save(Schoolboy schoolboy);

    Schoolboy update(Schoolboy schoolboy);

    void delete(Schoolboy schoolboy);

    void deleteById(Long id);
}
