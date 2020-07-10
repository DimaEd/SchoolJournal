package com.ednach.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * this is User class associated with the database
 */
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{user.firstName.notNull}")
    @NotEmpty(message = "{user.firstName.notEmpty}")
    @Size(min = 3, max = 50, message = "{user.firstName.size}")
    private String firstName;

    @NotNull(message = "{user.lastName.notNull}")
    @NotEmpty(message = "{user.lastName.notEmpty}")
    @Size(min = 3, max = 50, message = "{user.lastName.size}")
    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "User_Role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @NotNull(message = "{user.role.notNull}")
    private Set<Role> roles;

    @OneToOne(mappedBy = "user")
    private Schoolboy schoolboy;

    @OneToOne(mappedBy = "user")
    private Teacher teacher;

    @Column(unique = true, nullable = false)
    @NotNull(message = "{user.email.notNull}")
    @NotEmpty(message = "{user.email.notEmpty}")
    @Size(min = 6, max = 50, message = "{user.email.size}")
    private String email;

    @Column(unique = true, nullable = false)
    @NotNull(message = "{user.password.notNull}")
    @NotEmpty(message = "{user.password.notEmpty}")
    @Size(min = 3, max = 100, message = "{user.password.size}")
    private String password;

    public User(Long id, String firstName, String lastName, Set<Role> roles, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
        this.email = email;
        this.password = password;
    }
}
