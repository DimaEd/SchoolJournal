package com.ednach.service.impl;

import com.ednach.model.Schoolboy;
import com.ednach.service.SinService;
import com.ednach.service.TeacherService;
import com.ednach.component.LocalizedMessageSource;
import com.ednach.repository.SinRepository;
import com.ednach.model.Sin;
import com.ednach.service.SchoolboyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of service interface for Sin entity
 */
@RequiredArgsConstructor
@Service
@Transactional
public class SinServiceImpl implements SinService {
    final SinRepository sinRepository;
    final LocalizedMessageSource localizedMessageSource;
    final SchoolboyService schoolboyService;
    final TeacherService teacherService;

    @Override
    public Sin findByTypeSin(String typeSin) {
        return sinRepository.findByTypeSin(typeSin);
    }

    @Override
    public List<Sin> findBySchoolboy(Schoolboy schoolboy) {
        return sinRepository.findBySchoolboy(schoolboy);
    }

    @Override
    public List<Sin> findAll() {
        return sinRepository.findAll();
    }

    @Override
    public Sin findById(Long id) {
        return sinRepository.findById(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.sin.notExist", new Object[]{})));

    }

    @Override
    public Sin save(Sin sin) {
        validate(sin.getId() != null, localizedMessageSource.getMessage("error.sin.notHaveId", new Object[]{}));
        return saveAndFlush(sin);
    }

    @Override
    public Sin update(Sin sin) {
        final Long id = sin.getId();
        validate(id == null, localizedMessageSource.getMessage("error.sin.haveId", new Object[]{}));
        final Sin duplicateSin = sinRepository.findByTypeSin(sin.getTypeSin());
        final boolean isDuplicateExists = duplicateSin != null && !Objects.equals(duplicateSin.getId(), id);
        validate(isDuplicateExists, localizedMessageSource.getMessage("error.sin.typeSin.notUnique", new Object[]{}));
        return saveAndFlush(sin);
    }

    @Override
    public void delete(Sin sin) {
        final Long id = sin.getId();
        validate(sin.getId() == null, localizedMessageSource.getMessage("error.sin.haveId", new Object[]{}));
       findById(id);
        sinRepository.delete(sin);
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        sinRepository.deleteById(id);
    }

    private Sin saveAndFlush(Sin sin) {
        validate(sin.getTeacher() == null || sin.getTeacher().getId() == null, localizedMessageSource.getMessage("error.sin.teacher.isNull", new Object[]{}));
        validate(sin.getSchoolboy() == null || sin.getSchoolboy().getId() == null, localizedMessageSource.getMessage("error.sin.schoolboy.isNull", new Object[]{}));
        sin.setTeacher(teacherService.findById(sin.getTeacher().getId()));
        sin.setSchoolboy(schoolboyService.findById(sin.getSchoolboy().getId()));
        return sinRepository.saveAndFlush(sin);
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }
}
