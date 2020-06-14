package com.ednach.service;

import com.ednach.model.Schoolboy;

import java.util.List;

/**
 * Interface with CRUD methods for Schoolboy entity
 */
public interface SchoolboyService {

    List<Schoolboy> findAll();

    Schoolboy getOne (long id);

    Schoolboy findById(Long id);

    Schoolboy save(Schoolboy schoolboy);

    Schoolboy update(Schoolboy schoolboy);

    void delete(Schoolboy schoolboy);

    void deleteById(Long id);
}
