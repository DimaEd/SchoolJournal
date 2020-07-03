package com.ednach.service.impl;

import com.ednach.model.Discipline;
import com.ednach.service.ScheduleService;
import com.ednach.service.TeacherService;
import com.ednach.component.LocalizedMessageSource;
import com.ednach.repository.DisciplineRepository;
import com.ednach.service.DisciplineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of service interface for Discipline entity
 */
@RequiredArgsConstructor
@Service
@Transactional
public class DisciplineServiceImpl implements DisciplineService {

    final DisciplineRepository disciplineRepository;
    final LocalizedMessageSource localizedMessageSource;
    final TeacherService teacherService;

    @Override
    public List<Discipline> findAll() {
        return disciplineRepository.findAll();
    }

    @Override
    public Discipline findById(Long id) {
        return disciplineRepository.findById(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.discipline.notExist", new Object[]{})));
    }

    @Override
    public Discipline save(Discipline discipline) {
        validate(discipline.getId() != null, localizedMessageSource.getMessage("error.discipline.notHaveId", new Object[]{}));
        validate(disciplineRepository.existsByNameSubject(discipline.getNameSubject()), localizedMessageSource.getMessage("error.discipline.subject.notUnique", new Object[]{}));
        return saveAndFlush(discipline);
    }

    @Override
    public Discipline update(Discipline discipline) {
        final Long id = discipline.getId();
        validate(id == null, localizedMessageSource.getMessage("error.discipline.haveId", new Object[]{}));
        findById(id);
        final Discipline duplicateDiscipline = disciplineRepository.findByNameSubject(discipline.getNameSubject());
        final boolean isDuplicateExists = duplicateDiscipline != null && !Objects.equals(duplicateDiscipline.getId(), id);
        validate(isDuplicateExists, localizedMessageSource.getMessage("error.discipline.subject.notUnique", new Object[]{}));

        return saveAndFlush(discipline);
    }

    @Override
    public void delete(Discipline discipline) {
        final Long id = discipline.getId();
        validate(id == null, localizedMessageSource.getMessage("error.discipline.haveId", new Object[]{}));
        findById(id);
        disciplineRepository.delete(discipline);


    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        disciplineRepository.deleteById(id);

    }

    private Discipline saveAndFlush(Discipline discipline) {
        validate(discipline.getTeacher() == null || discipline.getTeacher().getId() == null, localizedMessageSource.getMessage("error.discipline.teacher.isNull", new Object[]{}));
        discipline.setTeacher(teacherService.findById(discipline.getTeacher().getId()));
        return disciplineRepository.saveAndFlush(discipline);
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }
}
