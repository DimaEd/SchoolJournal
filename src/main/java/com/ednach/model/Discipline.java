package com.ednach.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * this is Discipline class associated with the database
 */
@Getter
@Setter
@Entity
@Table(name = "disciplines")
public class Discipline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teachers_id")
    @NotNull(message = "{discipline.teacher.notNull}")
    private Teacher teacher;

    @Column(unique = true, nullable = false)
    @NotNull(message = "{discipline.nameSubject.notNull}")
    @NotEmpty(message = "{discipline.nameSubject.notEmpty}")
    @Size(min = 3, max = 50, message = "{discipline.nameSubject.size}")
    private String nameSubject;

    @OneToMany(mappedBy = "discipline")
    private Set<Schedule> schedules;
}
