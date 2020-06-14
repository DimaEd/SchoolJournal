package com.ednach.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO (Data transfer object) class for Role entity.
 * Both for response and request
 */
@Getter
@Setter
public class RoleDto {

    private Long id;

    @NotNull(message = "{role.name.notNull}")
    @NotEmpty(message = "{role.name.notEmpty}")
    @Size(min = 3, max = 50, message = "{role.name.size}")
    private String name;
}
