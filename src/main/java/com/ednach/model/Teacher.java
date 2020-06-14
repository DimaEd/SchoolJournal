package com.ednach.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * this is Teacher class associated with the database
 */
@Getter
@Setter
@Entity
@Table
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
  //  @NotNull(message = "{teacher.id.notNull}")
    private Long id;

    @OneToMany(mappedBy = "teacher",fetch = FetchType.EAGER)
    private Set<Discipline> discipline;

    @OneToOne(mappedBy = "teacher")
    private Classroom classroom;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull(message = "{teacher.user.notNull}")
    private User user;

    @OneToMany(mappedBy = "teacher")
    private Set<Grade> grades;

    @OneToMany(mappedBy = "teacher")
    private Set<Sin> sins;

    public Teacher() {
    }

    public Teacher(Long id, User user) {
        this.id = id;
        this.user = user;
    }
}
