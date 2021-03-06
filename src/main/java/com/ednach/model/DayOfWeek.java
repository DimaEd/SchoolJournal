package com.ednach.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * this is DayOfWeek class associated with the database
 */
@Getter
@Setter
@Entity
@Table
public class DayOfWeek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "dayOfWeek")
    private Set<Schedule> schedules;

    private String day;

    public DayOfWeek() {
    }

    public DayOfWeek(Long id, String day) {
        this.id = id;
        this.day = day;
    }
}
