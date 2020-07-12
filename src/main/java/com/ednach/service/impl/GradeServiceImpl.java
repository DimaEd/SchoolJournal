package com.ednach.service.impl;

import com.ednach.component.LocalizedMessageSource;
import com.ednach.model.Grade;
import com.ednach.model.Schoolboy;
import com.ednach.service.DisciplineService;
import com.ednach.service.GradeService;
import com.ednach.service.SchoolboyService;
import com.ednach.service.TeacherService;
import com.ednach.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of service interface for Grade entity
 */
@RequiredArgsConstructor
@Service
@Transactional
public class GradeServiceImpl implements GradeService {
    final GradeRepository gradeRepository;
    final TeacherService teacherService;
    final SchoolboyService schoolboyService;
    final DisciplineService disciplineService;
    final LocalizedMessageSource localizedMessageSource;

    /**
     * Finds all Grade entities
     *
     * @return - List of Grade entities
     */
    @Override
    public List<Grade> findAll() {
        return gradeRepository.findAll();
    }

    /**
     * Finds the Grade entity with the given id
     *
     * @param id - Grade entity id
     * @return - Grade entity
     */
    @Override
    public Grade findById(Long id) {
        return gradeRepository.findById(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.grade.notExist", new Object[]{})));
    }

    /**
     * Finds all Grade entity with the given schoolboy
     *
     * @param schoolboy - Grade entity schoolboy
     * @return - Grade entity
     */
    @Override
    public List<Grade> findBySchoolboy(Schoolboy schoolboy) {
        return gradeRepository.findBySchoolboy(schoolboy);
    }

    /**
     * Saves a given grade entity
     *
     * @param grade - grade Entity
     * @return - the saved grade entity
     */
    @Override
    public Grade save(Grade grade) {
        validate(grade.getId() != null, localizedMessageSource.getMessage("error.grade.notHaveId", new Object[]{}));
        return gradeRepository.saveAndFlush(grade);
    }

    /**
     * Updates a grade entity and flushes changes instantly
     *
     * @param grade - grade entity
     * @return - the updated grade entity
     */
    @Override
    public Grade update(Grade grade) {
        final Long id = grade.getId();
        validate(id == null, localizedMessageSource.getMessage("error.grade.haveId", new Object[]{}));
        findById(id);
        return saveAndFlush(grade);
    }

    /**
     * Deletes the grade entity with the given id
     *
     * @param id - grade entity id
     */
    @Override
    public void deleteById(Long id) {
        findById(id);
        gradeRepository.deleteById(id);
    }

    /**
     * Deletes a given grade entity
     *
     * @param grade - grade entity
     */
    @Override
    public void delete(Grade grade) {
        final Long id = grade.getId();
        validate(grade.getId() == null, localizedMessageSource.getMessage("error.grade.haveId", new Object[]{}));
        findById(id);
        gradeRepository.delete(grade);
    }

    private Grade saveAndFlush(Grade grade) {
        validate(grade.getTeacher() == null || grade.getTeacher().getId() == null, localizedMessageSource.getMessage("error.grade.teacher.isNull", new Object[]{}));
        validate(grade.getSchoolboy() == null || (grade.getSchoolboy().getId() == null), localizedMessageSource.getMessage("error.grade.user.isNull", new Object[]{}));
        validate(grade.getDiscipline() == null || (grade.getDiscipline().getId() == null), localizedMessageSource.getMessage("error.grade.discipline.isNull", new Object[]{}));
        grade.setDiscipline(disciplineService.findById(grade.getDiscipline().getId()));
        grade.setSchoolboy(schoolboyService.findById(grade.getSchoolboy().getId()));
        grade.setTeacher(teacherService.findById(grade.getTeacher().getId()));
        return gradeRepository.saveAndFlush(grade);

    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }
}
