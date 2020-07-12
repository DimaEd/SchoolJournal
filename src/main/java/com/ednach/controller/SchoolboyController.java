package com.ednach.controller;

import com.ednach.dto.request.SchoolboyRequestDto;
import com.ednach.dto.response.ClassroomProjectionResponseDto;
import com.ednach.dto.response.SchoolboyProjectionResponseDto;
import com.ednach.dto.response.SchoolboyResponseDto;
import com.ednach.dto.response.UserResponseDto;
import com.ednach.model.Schoolboy;
import com.ednach.model.User;
import com.ednach.component.LocalizedMessageSource;
import com.ednach.model.Classroom;
import com.ednach.repository.projection.SchoolboyProjection;
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

    private final Mapper mapper;
    private final LocalizedMessageSource localizedMessageSource;
    private final SchoolboyService schoolboyService;

    /**
     * Finds all Schoolboy entities
     *
     * @return - ResponseEntity with the given body and status code
     */
    @GetMapping
    public ResponseEntity<List<SchoolboyProjectionResponseDto>> getAll() {
        final List<SchoolboyProjection> schoolboys = schoolboyService.findAll();
        final List<SchoolboyProjectionResponseDto> schoolboyResponseDtoList = schoolboys.stream()
                .map(this::convertFromSchoolboyProjection)
                .collect(Collectors.toList());
        return new ResponseEntity<>(schoolboyResponseDtoList, HttpStatus.OK);
    }

    /**
     * Finds schoolboy entity by id and maps it to DTO
     *
     * @param id - schoolboy entity id
     * @return - ResponseEntity with the given body and status code
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<SchoolboyResponseDto> getOne(@PathVariable Long id) {
        final SchoolboyResponseDto schoolboyResponseDto = mapper.map(schoolboyService.findById(id), SchoolboyResponseDto.class);
        return new ResponseEntity<>(schoolboyResponseDto, HttpStatus.OK);
    }

    /**
     * Saves schoolboy entity with transferred parameters and maps it to DTO
     *
     * @param schoolboyRequestDto - the body of the web request
     * @return - ResponseEntity with the given body and status code
     */
    @PostMapping
    public ResponseEntity<SchoolboyResponseDto> save(@Valid @RequestBody SchoolboyRequestDto schoolboyRequestDto) {
        schoolboyRequestDto.setId(null);
        final SchoolboyResponseDto schoolboyResponseDto = mapper.map(schoolboyService.save(getSchoolboy(schoolboyRequestDto)), SchoolboyResponseDto.class);
        return new ResponseEntity<>(schoolboyResponseDto, HttpStatus.OK);
    }

    /**
     * Updates schoolboy entity with transferred parameters by entities id and maps it to DTO
     *
     * @param schoolboyRequestDto - the body of the web request
     * @param id                  - schoolboy entity id
     * @return - ResponseEntity with the given body and status code
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<SchoolboyResponseDto> update(@Valid @RequestBody SchoolboyRequestDto schoolboyRequestDto, @PathVariable Long id) {
        if (!Objects.equals(id, schoolboyRequestDto.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.schoolboy.unexpectedId", new Object[]{}));
        }
        final SchoolboyResponseDto schoolboyResponseDto = mapper.map(schoolboyService.update(getSchoolboy(schoolboyRequestDto)), SchoolboyResponseDto.class);
        return new ResponseEntity<>(schoolboyResponseDto, HttpStatus.OK);
    }

    /**
     * Deletes schoolboy entity by its id
     *
     * @param id - schoolboy entity id
     */
    @DeleteMapping(value = "/{id}")
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

    /**
     * create SchoolboyProjectionResponseDto entity for response
     *
     * @param schoolboyProjection - the body of the SchoolboyProjection
     * @return - schoolboyProjectionResponseDto with the given body
     */
    private SchoolboyProjectionResponseDto convertFromSchoolboyProjection(SchoolboyProjection schoolboyProjection) {
        SchoolboyProjectionResponseDto schoolboyProjectionResponseDto = new SchoolboyProjectionResponseDto();
        UserResponseDto userResponseDto = new UserResponseDto();
        ClassroomProjectionResponseDto classroomProjectionResponseDto = new ClassroomProjectionResponseDto();

        schoolboyProjectionResponseDto.setId(schoolboyProjection.getUserId());

        userResponseDto.setId(schoolboyProjection.getUserId());
        userResponseDto.setFirstName(schoolboyProjection.getUserFirstName());
        userResponseDto.setLastName(schoolboyProjection.getUserLastName());
        schoolboyProjectionResponseDto.setUser(userResponseDto);

        classroomProjectionResponseDto.setId(schoolboyProjection.getClassroomId());
        classroomProjectionResponseDto.setClassName(schoolboyProjection.getClassName());
        schoolboyProjectionResponseDto.setClassroom(classroomProjectionResponseDto);

        return schoolboyProjectionResponseDto;
    }
}



