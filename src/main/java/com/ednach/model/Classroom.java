package com.ednach.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * this is Classroom class associated with the database
 */
@Getter
@Setter
@Entity
@Table
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "classroom")
    private Schoolboy schoolboy;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    @NotNull(message = "{classroom.teacher.notNull}")
    private Teacher teacher;

    @Column(unique = true, nullable = false)
    @NotNull(message = "{classroom.className.notNull}")
    @NotEmpty(message = "{classroom.className.notEmpty}")
    @Size(min = 1, max = 10, message = "{classroom.className.size}")
    private String className;

    @OneToMany(mappedBy = "classroom")
    private Set<Schedule> schedules;

    public Classroom() {
    }

    public Classroom(Long id, Schoolboy schoolboy, Teacher teacher, String className) {
        this.id = id;
        this.schoolboy = schoolboy;
        this.teacher = teacher;
        this.className = className;
    }
}
