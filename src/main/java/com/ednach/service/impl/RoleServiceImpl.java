package com.ednach.service.impl;

import com.ednach.component.LocalizedMessageSource;
import com.ednach.repository.RoleRepository;
import com.ednach.model.Role;
import com.ednach.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of service interface for Role entity
 */
@RequiredArgsConstructor
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final LocalizedMessageSource localizedMessageSource;
    private final RoleRepository roleRepository;

    /**
     * Finds all Role entities
     *
     * @return - List of Role entities
     */
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    /**
     * Finds the Role entity with the given roleName
     *
     * @param roleName - Role entity roleName
     * @return - Role entity
     */
    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    /**
     * Finds the Role entity with the given id
     *
     * @param id - Role entity id
     * @return - Role entity
     */
    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.role.notExist", new Object[]{})));
    }

    /**
     * Saves a given role entity
     *
     * @param role - role Entity
     * @return - the role entity
     */
    @Override
    public Role save(Role role) {
        validate(role.getId() != null, localizedMessageSource.getMessage("error.role.notHaveId", new Object[]{}));
        validate(roleRepository.existsByName(role.getName()), localizedMessageSource.getMessage("error.role.name.notUnique", new Object[]{}));
        return roleRepository.saveAndFlush(role);
    }

    /**
     * Updates a role entity and flushes changes instantly
     *
     * @param role - role entity
     * @return - the updated role entity
     */
    @Override
    public Role update(Role role) {
        final Long id = role.getId();
        validate(id == null, localizedMessageSource.getMessage("error.role.haveId", new Object[]{}));
        final Role duplicateRole = roleRepository.findByName(role.getName());
        findById(id);
        final boolean isDuplicateExists = duplicateRole != null && !Objects.equals(duplicateRole.getId(), id);
        validate(isDuplicateExists, localizedMessageSource.getMessage("error.role.name.notUnique", new Object[]{}));
        return roleRepository.saveAndFlush(role);
    }

    /**
     * Deletes a given role entity
     *
     * @param role - role entity
     */
    @Override
    public void delete(Role role) {
        final Long id = role.getId();
        validate(role.getId() == null, localizedMessageSource.getMessage("error.role.haveId", new Object[]{}));
        findById(id);
        roleRepository.delete(role);
    }

    /**
     * Deletes the role entity with the given id
     *
     * @param id - role entity id
     */
    @Override
    public void deleteById(Long id) {
        findById(id);
        roleRepository.deleteById(id);
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }
}