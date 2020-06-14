package com.ednach.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * this is Sin class associated with the database
 */
@Getter
@Setter
@Entity
@Table
public class Sin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "schoolboy_id", nullable = false)
    @NotNull(message = "{sin.schoolboy.notNull}")
    private Schoolboy schoolboy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id", nullable = false)
    @NotNull(message = "{sin.teacher.notNull}")
    private Teacher teacher;

    @Column(unique = true, nullable = false)
    @NotNull(message = "{sin.typeSin.notNull}")
    @NotEmpty(message = "{sin.typeSin.notEmpty}")
    @Size(min = 3, max = 50, message = "{sin.typeSin.size}")
    private String typeSin;

    @NotNull(message = "{sin.points.notNull}")
  //  @NotEmpty(message = "{sin.points.notEmpty}")
    private Long points;

    public Sin() {
    }

    public Sin(Long id, Schoolboy schoolboy, Teacher teacher, String typeSin, Long points) {
        this.id = id;
        this.schoolboy = schoolboy;
        this.teacher = teacher;
        this.typeSin = typeSin;
        this.points = points;
    }
}
