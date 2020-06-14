package com.ednach.service.impl;

import com.ednach.repository.ClassroomRepository;
import com.ednach.service.TeacherService;
import com.ednach.component.LocalizedMessageSource;
import com.ednach.model.Classroom;
import com.ednach.service.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of service interface for Classroom entity
 */
@Service
@Transactional
public class ClassroomServiceImpl implements ClassroomService {
    final ClassroomRepository classroomRepository;
    final LocalizedMessageSource localizedMessageSource;
    final TeacherService teacherService;

    public ClassroomServiceImpl(ClassroomRepository classroomRepository, LocalizedMessageSource localizedMessageSource, TeacherService teacherService) {
        this.classroomRepository = classroomRepository;
        this.localizedMessageSource = localizedMessageSource;
        this.teacherService = teacherService;
    }

    /**
     * Finds all classroom entities
     *
     * @return - List of Classroom entities
     */
    @Override
    public List<Classroom> findAll() {
        return classroomRepository.findAll();
    }

    /**
     * Finds the Classroom entity with the given id
     *
     * @param id - Classroom entity id
     * @return - Classroom entity
     */
    @Override
    public Classroom findById(Long id) {
        return classroomRepository.findById(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.classroom.notExist", new Object[]{})));
    }

    /**
     * Finds the Classroom entity with the given classroom
     *
     * @param classroom - classroom entity classroom
     * @return - classroom entity
     */
    @Override
    public Classroom findByClassName(String classroom) {
        return classroomRepository.findByClassName(classroom);
    }

    /**
     * Saves a given classroom entity
     *
     * @param classroom - classroom Entity
     * @return - the saved classroom entity
     */
    @Override
    public Classroom save(Classroom classroom) {
        validate(classroom.getId() != null, localizedMessageSource.getMessage("error.classroom.notHaveId", new Object[]{}));
        validate(classroomRepository.existsByClassName(classroom.getClassName()), localizedMessageSource.getMessage("error.classroom.className.notUnique", new Object[]{}));
        return saveAndFlush(classroom);
    }

    /**
     * Updates a classroom entity and flushes changes instantly
     *
     * @param classroom - classroom entity
     * @return - the updated classroom entity
     */
    @Override
    public Classroom update(Classroom classroom) {
        final Long id = classroom.getId();
        validate(id == null, localizedMessageSource.getMessage("error.classroom.haveId", new Object[]{}));
        final Classroom duplicateClassroom = classroomRepository.findByClassName(classroom.getClassName());
        final boolean isDuplicateExists = duplicateClassroom != null && !Objects.equals(duplicateClassroom.getId(), id);
        validate(isDuplicateExists, localizedMessageSource.getMessage("error.classroom.className.notUnique", new Object[]{}));
        return saveAndFlush(classroom);
    }

    /**
     * Deletes the classroom entity with the given id
     *
     * @param id - classroom entity id
     */
    @Override
    public void deleteById(Long id) {
        classroomRepository.findById(id);
    }

    /**
     * Deletes a given classroom entity
     *
     * @param classroom - classroom entity
     */
    @Override
    public void delete(Classroom classroom) {
        validate(classroom.getId() == null, localizedMessageSource.getMessage("error.classroom.haveId", new Object[]{}));
    }

    private Classroom saveAndFlush(Classroom classroom) {
        validate(classroom.getTeacher() == null || classroom.getTeacher().getId() == null, localizedMessageSource.getMessage("error.classroom.teacher.isNull", new Object[]{}));
        classroom.setTeacher(teacherService.findById(classroom.getTeacher().getId()));
        return classroomRepository.saveAndFlush(classroom);
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }
}
