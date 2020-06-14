package com.ednach.controller;

import com.ednach.component.LocalizedMessageSource;
import com.ednach.dto.request.ClassroomRequestDto;
import com.ednach.dto.responce.ClassroomResponseDto;
import com.ednach.model.Classroom;
import com.ednach.model.Teacher;
import com.ednach.service.ClassroomService;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Controller for classroom entity
 */
@RestController
@RequestMapping("/classrooms")
public class ClassroomController {
    final Mapper mapper;
    final ClassroomService classroomService;
    final LocalizedMessageSource localizedMessageSource;

    public ClassroomController(Mapper mapper, ClassroomService classroomService, LocalizedMessageSource localizedMessageSource) {
        this.mapper = mapper;
        this.classroomService = classroomService;
        this.localizedMessageSource = localizedMessageSource;
    }


    /**
     * Finds all classroom entities and maps them to DTO
     *
     * @return - ResponseEntity with the given body and status code
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ClassroomResponseDto>> getAll() {
        final List<Classroom> classrooms = classroomService.findAll();
        final List<ClassroomResponseDto> classroomResponseDtoList = classrooms.stream()
                .map((classroom) -> mapper.map(classroom, ClassroomResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(classroomResponseDtoList, HttpStatus.OK);
    }

    /**
     * Finds classroom entity by id and maps it to DTO
     *
     * @param id - classroom entity id
     * @return - ResponseEntity with the given body and status code
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ClassroomResponseDto> getOne(@PathVariable Long id) {
        final ClassroomResponseDto classroomResponseDto = mapper.map(classroomService.findById(id), ClassroomResponseDto.class);
        return new ResponseEntity<>(classroomResponseDto, HttpStatus.OK);
    }

    /**
     * Saves classroom entity with transferred parameters and maps it to DTO
     *
     * @param classroomRequestDto - the body of the web request
     * @return - ResponseEntity with the given body and status code
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ClassroomResponseDto> save(@Valid @RequestBody ClassroomRequestDto classroomRequestDto) {
        classroomRequestDto.setId(null);
        final ClassroomResponseDto classroomResponseDto = mapper.map(classroomService.save(getClassroom(classroomRequestDto)), ClassroomResponseDto.class);
        return new ResponseEntity<>(classroomResponseDto, HttpStatus.OK);
    }

    /**
     * Updates classroom entity with transferred parameters by entities id and maps it to DTO
     *
     * @param classroomRequestDto - the body of the web request
     * @param id                  - classroom entity id
     * @return - ResponseEntity with the given body and status code
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ClassroomResponseDto> update(@Valid @RequestBody ClassroomRequestDto classroomRequestDto, @PathVariable Long id) {
        if (!Objects.equals(id, classroomRequestDto.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.classroom.unexpectedId", new Object[]{}));
        }
        final ClassroomResponseDto classroomResponseDto = mapper.map(classroomService.update(getClassroom(classroomRequestDto)), ClassroomResponseDto.class);
        return new ResponseEntity<>(classroomResponseDto, HttpStatus.OK);
    }

    /**
     * Deletes classroom entity by its id
     *
     * @param id - classroom entity id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        classroomService.deleteById(id);
    }

    private Classroom getClassroom(ClassroomRequestDto classroomRequestDto) {
        final Classroom classroom = mapper.map(classroomRequestDto, Classroom.class);
        final Teacher teacher = new Teacher();
        teacher.setId(classroomRequestDto.getTeacherId());
        classroom.setTeacher(teacher);
        return classroom;
    }

}
