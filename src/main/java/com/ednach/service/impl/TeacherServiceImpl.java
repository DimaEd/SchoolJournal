package com.ednach.service.impl;

import com.ednach.repository.TeacherRepository;
import com.ednach.component.LocalizedMessageSource;
import com.ednach.model.Teacher;
import com.ednach.service.TeacherService;
import com.ednach.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of service interface for Teacher entity
 */

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    final LocalizedMessageSource localizedMessageSource;
    final UserService userService;
    final TeacherRepository teacherRepository;

    public TeacherServiceImpl(LocalizedMessageSource localizedMessageSource, UserService userService, TeacherRepository teacherRepository) {
        this.localizedMessageSource = localizedMessageSource;
        this.userService = userService;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher findById(Long id) {
        return teacherRepository.findById(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.teacher.notExist", new Object[]{})));
    }

    @Override
    public Teacher save(Teacher teacher) {
        validate(teacher.getId() != null, localizedMessageSource.getMessage("error.teacher.notHaveId", new Object[]{}));
        validate(teacherRepository.existsByUser(teacher.getUser()), localizedMessageSource.getMessage("error.teacher.user.notUnique", new Object[]{}));
        return saveAndFlush(teacher);
    }

    @Override
    public Teacher update(Teacher teacher) {
        final Long id = teacher.getId();
        validate(id == null, localizedMessageSource.getMessage("error.teacher.haveId", new Object[]{}));
        final Teacher duplicateTeacher = teacherRepository.findByUser(teacher.getUser());
        final boolean isDuplicateExists = duplicateTeacher != null && !Objects.equals(duplicateTeacher.getId(), id);
        validate(isDuplicateExists, localizedMessageSource.getMessage("error.teacher.user.notUnique", new Object[]{}));
        return saveAndFlush(teacher);
    }

    @Override
    public void delete(Teacher teacher) {
        final Long id = teacher.getId();
        validate(id == null, localizedMessageSource.getMessage("error.teacher.haveId", new Object[]{}));
       findById(id);
        teacherRepository.delete(teacher);
    }

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
