package com.ednach.service.impl;

import com.ednach.component.LocalizedMessageSource;
import com.ednach.repository.SchoolboyRepository;
import com.ednach.model.Schoolboy;
import com.ednach.service.SchoolboyService;
import com.ednach.service.UserService;
import com.ednach.service.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ednach.repository.projection.SchoolboyProjection;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of service interface for Schoolboy entity
 */
@RequiredArgsConstructor
@Service
@Transactional
public class SchoolboyServiceImpl implements SchoolboyService {
    final SchoolboyRepository schoolboyRepository;
    final UserService userService;
    final ClassroomService classroomService;
    final LocalizedMessageSource localizedMessageSource;

    @Override
    public List<SchoolboyProjection> findAll() {
        return schoolboyRepository.findAllCustom();
    }


    @Override
    public Schoolboy findById(Long id) {
        return schoolboyRepository.findById(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.schoolboy.notExist", new Object[]{})));
    }

    @Override
    public Schoolboy save(Schoolboy schoolboy) {
        validate(schoolboy.getId() != null, localizedMessageSource.getMessage("error.schoolboy.notHaveId", new Object[]{}));
       validate(schoolboyRepository.existsByUser(schoolboy.getUser()), localizedMessageSource.getMessage("error.schoolboy.user.notUnique", new Object[]{}));
        return saveAndFlush(schoolboy);
    }

    @Override
    public Schoolboy update(Schoolboy schoolboy) {
        final Long id = schoolboy.getId();
        validate(id == null, localizedMessageSource.getMessage("error.schoolboy.haveId", new Object[]{}));
        final Schoolboy duplicateSchoolboy = schoolboyRepository.findByUser(schoolboy.getUser());
        final boolean isDuplicateExists = duplicateSchoolboy != null && !Objects.equals(duplicateSchoolboy.getId(), id);
       findById(id);
        validate(isDuplicateExists, localizedMessageSource.getMessage("error.schoolboy.user.notUnique", new Object[]{}));
        return saveAndFlush(schoolboy);
    }

    @Override
    public void delete(Schoolboy schoolboy) {
        final Long id = schoolboy.getId();
        validate(schoolboy.getId() == null, localizedMessageSource.getMessage("error.schoolboy.haveId", new Object[]{}));
       findById(id);
        schoolboyRepository.delete(schoolboy);
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        schoolboyRepository.deleteById(id);
    }

    private Schoolboy saveAndFlush(Schoolboy schoolboy) {
        validate(schoolboy.getClassroom() == null || schoolboy.getClassroom().getId() == null, localizedMessageSource.getMessage("error.schoolboy.classroom.isNull", new Object[]{}));
        validate(schoolboy.getUser() == null || schoolboy.getUser().getId() == null, localizedMessageSource.getMessage("error.schoolboy.user.isNull", new Object[]{}));
        schoolboy.setUser(userService.findById(schoolboy.getUser().getId()));
        schoolboy.setClassroom(classroomService.findById(schoolboy.getClassroom().getId()));
        return schoolboyRepository.saveAndFlush(schoolboy);
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }
}
