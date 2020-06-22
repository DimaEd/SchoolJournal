package com.ednach.repository;

import com.ednach.model.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * DayOfWeekRepository provides the necessary search operations
 */

public interface DayOfWeekRepository  extends JpaRepository<DayOfWeek, Long> {

    DayOfWeek findByDayOfWeek(String day);
}
