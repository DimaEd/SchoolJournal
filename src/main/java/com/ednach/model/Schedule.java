package com.ednach.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * this is Schedule class associated with the database
 */
@Getter
@Setter
@Entity
@Table
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name = "classroom_id", nullable = false)
    @NotNull(message = "{schedule.classroom.notNull}")
    private Classroom classroom;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "dayOfWeek_id", nullable = false)
    @NotNull(message = "{schedule.dayWeek.notNull}")
    private DayOfWeek dayOfWeek;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "discipline_id", nullable = false)
    @NotNull(message = "{schedule.discipline.notNull}")
    private Discipline discipline;



    public Schedule() {
    }

    public Schedule(Long id, Classroom classroom, Discipline discipline, DayOfWeek dayOfWeek) {
        this.id = id;
        this.classroom = classroom;
        this.discipline = discipline;
        this.dayOfWeek = dayOfWeek;
    }
}
