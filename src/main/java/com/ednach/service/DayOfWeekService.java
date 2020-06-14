package com.ednach.service;

import com.ednach.model.DayOfWeek;

import java.util.List;

/**
 * Interface with CRUD methods for DayOfWeek entity
 */
public interface DayOfWeekService {

    List<DayOfWeek> findAll();

    DayOfWeek findById(Long id);

    DayOfWeek findByDayOfWeek(String day);
}
