package com.ednach.controller;

import com.ednach.dto.request.SchoolboyRequestDto;
import com.ednach.dto.responce.SchoolboyResponseDto;
import com.ednach.model.Schoolboy;
import com.ednach.model.User;
import com.ednach.component.LocalizedMessageSource;
import com.ednach.model.Classroom;
import com.ednach.service.SchoolboyService;
import lombok.RequiredArgsConstructor;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/schoolboys")
public class SchoolboyController {

    final private Mapper mapper;
    private final LocalizedMessageSource localizedMessageSource;
    private final SchoolboyService schoolboyService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<SchoolboyResponseDto>> getAll() {
        final List<Schoolboy> schoolboys = schoolboyService.findAll();
        final List<SchoolboyResponseDto> schoolboyResponseDtoList = schoolboys.stream()
                .map((schoolboy) -> mapper.map(schoolboy, SchoolboyResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(schoolboyResponseDtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<SchoolboyResponseDto> getOne(@PathVariable Long id) {
        final SchoolboyResponseDto schoolboyResponseDto = mapper.map(schoolboyService.findById(id), SchoolboyResponseDto.class);
        return new ResponseEntity<>(schoolboyResponseDto, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SchoolboyResponseDto> save(@Valid @RequestBody SchoolboyRequestDto schoolboyRequestDto) {
        schoolboyRequestDto.setId(null);
        final SchoolboyResponseDto schoolboyResponseDto = mapper.map(schoolboyService.save(getSchoolboy(schoolboyRequestDto)), SchoolboyResponseDto.class);
        return new ResponseEntity<>(schoolboyResponseDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<SchoolboyResponseDto> update(@Valid @RequestBody SchoolboyRequestDto schoolboyRequestDto, @PathVariable Long id) {
        if (!Objects.equals(id, schoolboyRequestDto.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.schoolboy.unexpectedId", new Object[]{}));
        }
        final SchoolboyResponseDto schoolboyResponseDto = mapper.map(schoolboyService.update(getSchoolboy(schoolboyRequestDto)), SchoolboyResponseDto.class);
        return new ResponseEntity<>(schoolboyResponseDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        schoolboyService.deleteById(id);
    }

    private Schoolboy getSchoolboy(SchoolboyRequestDto schoolboyRequestDto) {
        final Schoolboy schoolboy = mapper.map(schoolboyRequestDto, Schoolboy.class);
        final Classroom classroom = new Classroom();
        classroom.setId(schoolboyRequestDto.getClassroomId());
        schoolboy.setClassroom(classroom);
        
        final User user = new User();
        user.setId(schoolboyRequestDto.getUserId());
        schoolboy.setUser(user);
        return schoolboy;
    }
}



