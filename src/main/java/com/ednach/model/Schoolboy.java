package com.ednach.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * this is Schoolboy class associated with the database
 */
@Getter
@Setter
@Entity
@Table
public class Schoolboy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull(message = "{schoolboy.user.notNull}")
    private User user;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "classroom_id", referencedColumnName = "id")
    @NotNull(message = "{schoolboy.classroom.notNull}")
    private Classroom classroom;

    @OneToMany(mappedBy = "schoolboy")
    private Set<Sin> sins;

    @OneToMany(mappedBy = "schoolboy")
    private Set<Grade> grades;

    public Schoolboy() {
    }

    public Schoolboy(Long id, User user, Classroom classroom) {
        this.id = id;
        this.user = user;
        this.classroom = classroom;
    }
}