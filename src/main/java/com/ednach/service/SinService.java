package com.ednach.service;

import com.ednach.model.Schoolboy;
import com.ednach.model.Sin;
import com.ednach.repository.projection.SinProjection;

import java.util.List;

/**
 * Interface with CRUD methods for Sin entity
 */
public interface SinService {

    List<SinProjection> findAll();

    List<Sin> findAllByTypeSin(String typeSin);

    Sin findById(Long id);

    List<Sin> findBySchoolboy(Schoolboy schoolboy);

    Sin save(Sin sin);

    Sin update(Sin sin);

    void delete(Sin sin);

    void deleteById(Long id);
}
