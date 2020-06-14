package com.ednach.controller;

import com.ednach.dto.request.GradeRequestDto;
import com.ednach.model.Grade;
import com.ednach.model.Schoolboy;
import com.ednach.component.LocalizedMessageSource;
import com.ednach.dto.responce.GradeResponseDto;
import com.ednach.model.Teacher;
import com.ednach.service.GradeService;
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

/**
 * Controller for grade entity
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/grades")
public class GradeController {

    private final Mapper mapper;
    private final GradeService gradeService;
    private final SchoolboyService schoolboyService;
    private final LocalizedMessageSource localizedMessageSource;

    /**
     * Finds all grade entities and maps them to DTO
     *
     * @return - ResponseEntity with the given body and status code
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<GradeResponseDto>> getAll() {
        final List<Grade> grades = gradeService.findAll();
        final List<GradeResponseDto> gradeResponseDtoList = grades.stream()
                .map((grade) -> mapper.map(grade, GradeResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(gradeResponseDtoList, HttpStatus.OK);
    }

    /**
     * Finds all grade entities and maps them to DTO
     *
     * @param id - schoolboy entity id
     * @return ResponseEntity with the given body and status code
     */
    @RequestMapping(value = "/schoolboy/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<GradeResponseDto>> getAllGrades(@PathVariable Long id) {
        Schoolboy b = schoolboyService.getOne(id);
        final List<Grade> grades = gradeService.findBySchoolboy(b);
        final List<GradeResponseDto> gradeResponseDtoList = grades.stream()
                .map((grade) -> mapper.map(grade, GradeResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(gradeResponseDtoList, HttpStatus.OK);
    }

    /**
     * Finds grade entity by id and maps it to DTO
     *
     * @param id - grade entity id
     * @return - ResponseEntity with the given body and status code
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<GradeResponseDto> getOne(@PathVariable Long id) {
        final GradeResponseDto gradeResponseDto = mapper.map(gradeService.findById(id), GradeResponseDto.class);
        return new ResponseEntity<>(gradeResponseDto, HttpStatus.OK);
    }

    /**
     * Saves grade entity with transferred parameters and maps it to DTO
     *
     * @param gradeRequestDto - the body of the web request
     * @return - ResponseEntity with the given body and status code
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<GradeResponseDto> save(@Valid @RequestBody GradeRequestDto gradeRequestDto) {
        gradeRequestDto.setId(null);
        final GradeResponseDto gradeResponseDto = mapper.map(gradeService.save(getGrade(gradeRequestDto)), GradeResponseDto.class);
        return new ResponseEntity<>(gradeResponseDto, HttpStatus.OK);
    }

    /**
     * Updates discipline entity with transferred parameters by entities id and maps it to DTO
     *
     * @param gradeRequestDto - the body of the web request
     * @param id              - grade entity id
     * @return - ResponseEntity with the given body and status code
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<GradeResponseDto> update(@Valid @RequestBody GradeRequestDto gradeRequestDto, @PathVariable Long id) {
        if (!Objects.equals(id, gradeRequestDto.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.grade.unexpectedId", new Object[]{}));
        }
        final GradeResponseDto gradeResponseDto = mapper.map(gradeService.update(getGrade(gradeRequestDto)), GradeResponseDto.class);
        return new ResponseEntity<>(gradeResponseDto, HttpStatus.OK);
    }

    /**
     * Deletes grade entity by its id
     *
     * @param id - grade entity id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        gradeService.deleteById(id);
    }

    private Grade getGrade(GradeRequestDto gradeRequestDto) {
        final Grade grade = mapper.map(gradeRequestDto, Grade.class);
        final Schoolboy schoolboy = new Schoolboy();
        schoolboy.setId(gradeRequestDto.getSchoolboyId());
        grade.setSchoolboy(schoolboy);
        final Teacher teacher = new Teacher();
        teacher.setId(gradeRequestDto.getTeacherId());
        grade.setTeacher(teacher);
        return grade;
    }
}
