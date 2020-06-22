package com.ednach.service;

import com.ednach.model.Schoolboy;
import com.ednach.model.Sin;

import java.util.List;

/**
 * Interface with CRUD methods for Sin entity
 */
public interface SinService {

    List<Sin> findAll();

    Sin findById(Long id);

    Sin findByTypeSin(String typeSin);

    List<Sin> findBySchoolboy(Schoolboy schoolboy);

    Sin save(Sin sin);

    Sin update(Sin sin);

    void delete(Sin sin);

    void deleteById(Long id);
}
