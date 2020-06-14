package com.ednach.controller;

import com.ednach.model.Role;
import com.ednach.service.RoleService;
import com.ednach.component.LocalizedMessageSource;
import com.ednach.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Controller for role entity
 */
//@RequiredArgsConstructor
@RestController
@RequestMapping("/roles")
public class RoleController {

    private final Mapper mapper;
    private final RoleService roleService;
    private final LocalizedMessageSource localizedMessageSource;

    public RoleController(Mapper mapper, RoleService roleService, LocalizedMessageSource localizedMessageSource) {
        this.mapper = mapper;
        this.roleService = roleService;
        this.localizedMessageSource = localizedMessageSource;
    }

    /**
     * Finds all role entities and maps them to DTO
     *
     * @return - ResponseEntity with the given body and status code
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<RoleDto>> getAll() {
        final List<Role> roles = roleService.findAll();
        final List<RoleDto> roleDtoList = roles.stream()
                .map((role) -> mapper.map(role, RoleDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(roleDtoList, HttpStatus.OK);
    }

    /**
     * Finds role entity by id and maps it to DTO
     *
     * @param id - role entity id
     * @return - ResponseEntity with the given body and status code
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<RoleDto> getOne(@PathVariable Long id) {
        final RoleDto roleDto = mapper.map(roleService.findById(id), RoleDto.class);
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    /**
     * Saves role entity with transferred parameters and maps it to DTO
     *
     * @param role - the body of the web request
     * @return - ResponseEntity with the given body and status code
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RoleDto> save(@Valid @RequestBody RoleDto role) {
        final RoleDto roleDto = mapper.map(roleService.save(mapper.map(role, Role.class)), RoleDto.class);
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    /**
     * Updates role entity with transferred parameters by entities id and maps it to DTO
     *
     * @param role - the body of the web request
     * @param id   - role entity id
     * @return - ResponseEntity with the given body and status code
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<RoleDto> update(@Valid @RequestBody RoleDto role, @PathVariable Long id) {
        if (!Objects.equals(id, role.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.role.unexpectedId", new Object[]{}));
        }
        final RoleDto roleDto = mapper.map(roleService.update(mapper.map(role, Role.class)), RoleDto.class);
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    /**
     * Delete role entity by its id
     *
     * @param id - role entity id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        roleService.deleteById(id);
    }
}
