package com.ednach.service.impl;

import com.ednach.model.DayOfWeek;
import com.ednach.component.LocalizedMessageSource;
import com.ednach.repository.DayOfWeekRepository;
import com.ednach.service.DayOfWeekService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of service interface for DayOfWeek entity
 */

@RequiredArgsConstructor
@Service
@Transactional
public class DayOfWeekServiceImpl implements DayOfWeekService {

    private final LocalizedMessageSource localizedMessageSource;
    private final DayOfWeekRepository dayOfWeekRepository;

    @Override
    public List<DayOfWeek> findAll() {
        return dayOfWeekRepository.findAll();
    }

    @Override
    public DayOfWeek findById(Long id) {
        return dayOfWeekRepository.findById(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.dayOfDay.notExist", new Object[]{})));
    }

    @Override
    public DayOfWeek findByDayOfWeek(String day) {
        return dayOfWeekRepository.findByDay(day);
    }
}
