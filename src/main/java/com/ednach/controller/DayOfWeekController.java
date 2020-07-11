package com.ednach.controller;

import com.ednach.model.DayOfWeek;
import com.ednach.dto.DayOfWeekDto;
import com.ednach.service.DayOfWeekService;
import lombok.RequiredArgsConstructor;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dayOfWeek")
public class DayOfWeekController {

    private final Mapper mapper;
    private final DayOfWeekService dayOfWeekService;

    @GetMapping
    public ResponseEntity<List<DayOfWeekDto>> getAll() {
        final List<DayOfWeek> dayOfWeeks = dayOfWeekService.findAll();
        final List<DayOfWeekDto> dayOfWeekDtoList = dayOfWeeks.stream()
                .map((dayOfWeek) -> mapper.map(dayOfWeek, DayOfWeekDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(dayOfWeekDtoList, HttpStatus.OK);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<DayOfWeekDto> getOne(@PathVariable Long id) {
        final DayOfWeekDto dayOfWeekDto = mapper.map(dayOfWeekService.findById(id), DayOfWeekDto.class);
        return new ResponseEntity<>(dayOfWeekDto, HttpStatus.OK);
    }
}
