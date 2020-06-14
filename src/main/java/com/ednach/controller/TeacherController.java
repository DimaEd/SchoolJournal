package com.ednach.controller;

import com.ednach.model.User;
import com.ednach.component.LocalizedMessageSource;
import com.ednach.dto.request.TeacherRequestDto;
import com.ednach.dto.responce.TeacherResponseDto;
import com.ednach.model.Teacher;
import com.ednach.service.TeacherService;
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
@RequestMapping("/teachers")

public class TeacherController {
    private final Mapper mapper;
    private final TeacherService teacherService;
    private final LocalizedMessageSource localizedMessageSource;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<TeacherResponseDto>> getAll() {
        final List<Teacher> teachers = teacherService.findAll();
        final List<TeacherResponseDto> teacherDtoList = teachers.stream()
                .map((teacher) -> mapper.map(teacher, TeacherResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(teacherDtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<TeacherResponseDto> getOne(@PathVariable Long id) {
        final TeacherResponseDto teacherDto = mapper.map(teacherService.findById(id), TeacherResponseDto.class);
        return new ResponseEntity<>(teacherDto, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TeacherResponseDto> save(@Valid @RequestBody TeacherRequestDto teacherRequestDto) {
        teacherRequestDto.setId(null);
        final TeacherResponseDto teacherDto = mapper.map(teacherService.save(getTeacher(teacherRequestDto)), TeacherResponseDto.class);
        return new ResponseEntity<>(teacherDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<TeacherResponseDto> update(@Valid @RequestBody TeacherRequestDto teacherRequestDto, @PathVariable Long id) {
        if (!Objects.equals(id, teacherRequestDto.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.teacher.unexpectedId", new Object[]{}));
        }
        final TeacherResponseDto teacherDto = mapper.map(teacherService.update(getTeacher(teacherRequestDto)), TeacherResponseDto.class);
        return new ResponseEntity<>(teacherDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        teacherService.deleteById(id);
    }

    private Teacher getTeacher(TeacherRequestDto teacherRequestDto) {
        final Teacher teacher = mapper.map(teacherRequestDto, Teacher.class);
        final User user = new User();
        user.setId(teacherRequestDto.getUserId());
        teacher.setUser(user);
        return teacher;
    }
}
