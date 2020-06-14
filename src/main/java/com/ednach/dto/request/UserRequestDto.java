package com.ednach.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Request DTO (Data transfer object) class for User entity.
 */
@Getter
@Setter
public class UserRequestDto {

    private Long id;

    @NotNull(message = "{user.firstName.notNull}")
    @NotEmpty(message = "{user.firstName.notEmpty}")
    @Size(min = 3, max = 50, message = "{user.firstName.size}")
    private String firstName;

    @NotNull(message = "{user.lastName.notNull}")
    @NotEmpty(message = "{user.lastName.notEmpty}")
    @Size(min = 3, max = 50, message = "{user.lastName.size}")
    private String lastName;

    @NotNull(message = "{user.password.notNull}")
    @NotEmpty(message = "{user.password.notEmpty}")
    @Size(min = 3, max = 100, message = "{user.password.size}")
    private String password;

    @NotNull(message = "{user.role.notNull}")
    private Set<Long> rolesId;


}
