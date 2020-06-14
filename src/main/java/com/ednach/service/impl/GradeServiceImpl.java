package com.ednach.service.impl;

import com.ednach.component.LocalizedMessageSource;
import com.ednach.model.Grade;
import com.ednach.model.Schoolboy;
import com.ednach.service.GradeService;
import com.ednach.service.SchoolboyService;
import com.ednach.service.TeacherService;
import com.ednach.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
    final LocalizedMessageSource localizedMessageSource;

    @Override
    public List<Grade> findAll() {
        return gradeRepository.findAll();
    }

    @Override
    public Grade findById(Long id) {
        return gradeRepository.findById(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.grade.notExist", new Object[]{})));
    }

    @Override
    public List<Grade> findBySchoolboy(Schoolboy b) {
        return gradeRepository.findBySchoolboy(b);
    }

    @Override
    public Grade save(Grade grade) {
        validate(grade.getId() != null, localizedMessageSource.getMessage("error.grade.notHaveId", new Object[]{}));
        validate(gradeRepository.existsBySubject(grade.getSubject()), localizedMessageSource.getMessage("error.grade.subject.notUnique", new Object[]{}));
        return gradeRepository.saveAndFlush(grade);
    }

    @Override
    public Grade update(Grade grade) {
        final Long id = grade.getId();
        validate(id == null, localizedMessageSource.getMessage("error.grade.haveId", new Object[]{}));
         final Grade duplicateGrade = gradeRepository.findBySubject(grade.getSubject());
        final boolean isDuplicateExists = duplicateGrade != null && !Objects.equals(duplicateGrade.getId(), id);
        validate(isDuplicateExists, localizedMessageSource.getMessage("error.grade.subject.notUnique", new Object[]{}));
        return saveAndFlush(grade);
    }

    @Override
    public void deleteById(Long id) {
        gradeRepository.deleteById(id);
    }

    @Override
    public void delete(Grade grade) {
        validate(grade.getId() == null, localizedMessageSource.getMessage("error.grade.haveId", new Object[]{}));
        gradeRepository.delete(grade);
    }

    private Grade saveAndFlush(Grade grade) {
        validate(grade.getTeacher() == null|| grade.getTeacher().getId()==null, localizedMessageSource.getMessage("error.grade.teacher.isNull", new Object[]{}));
        validate(grade.getSchoolboy() == null || (grade.getSchoolboy().getId() == null), localizedMessageSource.getMessage("error.grade.user.isNull", new Object[]{}));
        grade.setSchoolboy(schoolboyService.findById(grade.getSchoolboy().getId()));
        grade.setTeacher(teacherService.findById(grade.getTeacher().getId()));
        return gradeRepository.saveAndFlush(grade);

    }
    private void validate(boolean expression, String errorMessage){
        if(expression){
            throw new RuntimeException(errorMessage);
        }
    }
}
