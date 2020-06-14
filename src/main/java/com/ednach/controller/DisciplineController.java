package com.ednach.controller;

import com.ednach.component.LocalizedMessageSource;
import com.ednach.dto.request.DisciplineRequestDto;
import com.ednach.dto.responce.DisciplineResponseDto;
import com.ednach.model.Discipline;
import com.ednach.model.Teacher;
import com.ednach.service.DisciplineService;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Controller for discipline entity
 */
@RestController
@RequestMapping("/disciplines")
public class DisciplineController {

    private final Mapper mapper;
    private final DisciplineService disciplineService;
    private final LocalizedMessageSource localizedMessageSource;

    public DisciplineController(Mapper mapper, DisciplineService disciplineService, LocalizedMessageSource localizedMessageSource) {
        this.mapper = mapper;
        this.disciplineService = disciplineService;
        this.localizedMessageSource = localizedMessageSource;
    }

    /**
     * Finds all discipline entities and maps them to DTO
     *
     * @return - ResponseEntity with the given body and status code
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<DisciplineResponseDto>> getAll() {
        final List<Discipline> disciplines = disciplineService.findAll();
        final List<DisciplineResponseDto> disciplineResponseDtoList = disciplines.stream()
                .map((discipline) -> mapper.map(discipline, DisciplineResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(disciplineResponseDtoList, HttpStatus.OK);
    }

    /**
     * Finds discipline entity by id and maps it to DTO
     *
     * @param id - discipline entity id
     * @return - ResponseEntity with the given body and status code
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<DisciplineResponseDto> getOne(@PathVariable Long id) {
        final DisciplineResponseDto disciplineResponseDto = mapper.map(disciplineService.findById(id), DisciplineResponseDto.class);
        return new ResponseEntity<>(disciplineResponseDto, HttpStatus.OK);
    }

    /**
     * Saves discipline entity with transferred parameters and maps it to DTO
     *
     * @param disciplineRequestDto - the body of the web request
     * @return - ResponseEntity with the given body and status code
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<DisciplineResponseDto> save(@Valid @RequestBody DisciplineRequestDto disciplineRequestDto) {
        disciplineRequestDto.setId(null);
        final DisciplineResponseDto disciplineResponseDto = mapper.map(disciplineService.save(getDiscipline(disciplineRequestDto)), DisciplineResponseDto.class);
        return new ResponseEntity<>(disciplineResponseDto, HttpStatus.OK);
    }

    /**
     * Updates discipline entity with transferred parameters by entities id and maps it to DTO
     *
     * @param disciplineRequestDto - the body of the web request
     * @param id                   - discipline entity id
     * @return - ResponseEntity with the given body and status code
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<DisciplineResponseDto> update(@Valid @RequestBody DisciplineRequestDto disciplineRequestDto, @PathVariable Long id) {
        if (!Objects.equals(id, disciplineRequestDto.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.discipline.unexpectedId", new Object[]{}));
        }
        final DisciplineResponseDto disciplineResponseDto = mapper.map(disciplineService.update(getDiscipline(disciplineRequestDto)), DisciplineResponseDto.class);
        return new ResponseEntity<>(disciplineResponseDto, HttpStatus.OK);
    }

    /**
     * Deletes discipline entity by its id
     *
     * @param id - discipline entity id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        disciplineService.deleteById(id);
    }

    private Discipline getDiscipline(DisciplineRequestDto disciplineRequestDto) {
        final Discipline discipline = mapper.map(disciplineRequestDto, Discipline.class);
        final Teacher teacher = new Teacher();
        teacher.setId(disciplineRequestDto.getTeacherId());
        discipline.setTeacher(teacher);
        return discipline;
    }
}
