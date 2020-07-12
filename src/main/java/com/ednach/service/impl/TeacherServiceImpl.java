package com.ednach.service.impl;

import com.ednach.repository.TeacherRepository;
import com.ednach.component.LocalizedMessageSource;
import com.ednach.model.Teacher;
import com.ednach.service.TeacherService;
import com.ednach.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of service interface for Teacher entity
 */
@RequiredArgsConstructor
@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    final LocalizedMessageSource localizedMessageSource;
    final UserService userService;
    final TeacherRepository teacherRepository;

    /**
     * Finds all Sin entities
     *
     * @return - List of Sin entities
     */
    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    /**
     * Finds the Teacher entity with the given id
     *
     * @param id - Teacher entity id
     * @return - Teacher entity
     */
    @Override
    public Teacher findById(Long id) {
        return teacherRepository.findById(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.teacher.notExist", new Object[]{})));
    }

    /**
     * Saves a given teacher entity
     *
     * @param teacher - teacher Entity
     * @return - the saved teacher entity
     */
    @Override
    public Teacher save(Teacher teacher) {
        validate(teacher.getId() != null, localizedMessageSource.getMessage("error.teacher.notHaveId", new Object[]{}));
        validate(teacherRepository.existsByUser(teacher.getUser()), localizedMessageSource.getMessage("error.teacher.user.notUnique", new Object[]{}));
        return saveAndFlush(teacher);
    }

    /**
     * Updates a teacher entity and flushes changes instantly
     *
     * @param teacher - teacher entity
     * @return - the updated teacher entity
     */
    @Override
    public Teacher update(Teacher teacher) {
        final Long id = teacher.getId();
        validate(id == null, localizedMessageSource.getMessage("error.teacher.haveId", new Object[]{}));
        final Teacher duplicateTeacher = teacherRepository.findByUser(teacher.getUser());
        findById(id);
        final boolean isDuplicateExists = duplicateTeacher != null && !Objects.equals(duplicateTeacher.getId(), id);
        validate(isDuplicateExists, localizedMessageSource.getMessage("error.teacher.user.notUnique", new Object[]{}));
        return saveAndFlush(teacher);
    }

    /**
     * Deletes a given teacher entity
     *
     * @param teacher - teacher entity
     */
    @Override
    public void delete(Teacher teacher) {
        final Long id = teacher.getId();
        validate(id == null, localizedMessageSource.getMessage("error.teacher.haveId", new Object[]{}));
        findById(id);
        teacherRepository.delete(teacher);
    }

    /**
     * Deletes the teacher entity with the given id
     *
     * @param id - teacher entity id
     */
    @Override
    public void deleteById(Long id) {
        findById(id);
        teacherRepository.deleteById(id);
    }

    private Teacher saveAndFlush(Teacher teacher) {
        validate(teacher.getUser().getId() == null, localizedMessageSource.getMessage("error.teacher.user.isNull", new Object[]{}));
        teacher.setUser(userService.findById(teacher.getUser().getId()));
        return teacherRepository.saveAndFlush(teacher);
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }
}
