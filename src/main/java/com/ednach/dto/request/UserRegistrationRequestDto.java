package com.ednach.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Request DTO (Data transfer object) class for User's registration.
 */
@Getter
@Setter
public class UserRegistrationRequestDto {

    @NotNull(message = "{user.firstName.notNull}")
    @NotEmpty(message = "{user.firstName.notEmpty}")
    @Size(min = 3, max = 50, message = "{user.firstName.size}")
    private String firstName;

    @NotNull(message = "{user.lastName.notNull}")
    @NotEmpty(message = "{user.lastName.notEmpty}")
    @Size(min = 3, max = 50, message = "{user.lastName.size}")
    private String lastName;

    @NotNull(message = "{user.email.notNull}")
    @NotEmpty(message = "{user.email.notEmpty}")
    @Size(min = 6, max = 50, message = "{user.email.size}")
    private String email;

    @NotNull(message = "{user.password.notNull}")
    @NotEmpty(message = "{user.password.notEmpty}")
    @Size(min = 3, max = 100, message = "{user.password.size}")
    private String password;

    @NotNull(message = "{user.role.notNull}")
    private Set<String> roles;
}