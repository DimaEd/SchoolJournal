package com.ednach.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * this is Grade class associated with the database
 */
@Getter
@Setter
@Entity
@Table
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "schoolboy_id", nullable = false)
    @NotNull(message = "{grade.schoolboy.notNull}")
    private Schoolboy schoolboy;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "discipline_id", nullable = false)
    @NotNull(message = "{grade.discipline.notNull}")
    private Discipline discipline;

    @NotNull(message = "{grade.mark.notNull}")
    private Long mark;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "teachers_id", nullable = false)
    @NotNull(message = "{grade.teacher.notNull}")
    private Teacher teacher;

    @Column(unique = true, nullable = false)
    @NotNull(message = "{grade.date.notNull}")
    @NotEmpty(message = "{grade.date.notEmpty}")
    @Size(min = 3, max = 50, message = "{grade.data.size}")
    private String date;

    public Grade(){}

    public Grade(Long id,Schoolboy schoolboy,Teacher teacher, Discipline discipline,Long mark, String date) {
        this.id=id;
        this.schoolboy = schoolboy;
        this.teacher = teacher;
        this.discipline = discipline ;
        this.mark = mark;
        this.date = date;
    }
}

