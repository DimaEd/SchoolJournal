package com.ednach.repository;

import com.ednach.model.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DayOfWeekRepository provides the necessary search operations
 */

public interface DayOfWeekRepository  extends JpaRepository<DayOfWeek, Long> {

    DayOfWeek findByDay(String day);
}
