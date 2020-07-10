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

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.role.notExist", new Object[]{})));
    }

    @Override
    public Role save(Role role) {
        validate(role.getId() != null, localizedMessageSource.getMessage("error.role.notHaveId", new Object[]{}));
        validate(roleRepository.existsByName(role.getName()), localizedMessageSource.getMessage("error.role.name.notUnique", new Object[]{}));
        return roleRepository.saveAndFlush(role);
    }

    @Override
    public Role update(Role role) {
        final Long id = role.getId();
       // validate(id == null, localizedMessageSource.getMessage("error.role.haveId", new Object[]{}));
        final Role duplicateRole = roleRepository.findByName(role.getName());
        findById(id);
        final boolean isDuplicateExists = duplicateRole != null && !Objects.equals(duplicateRole.getId(), id);
        validate(isDuplicateExists, localizedMessageSource.getMessage("error.role.name.notUnique", new Object[]{}));
        return roleRepository.saveAndFlush(role);
    }

    @Override
    public void delete(Role role) {
        final Long id = role.getId();
        validate(role.getId() == null, localizedMessageSource.getMessage("error.role.haveId", new Object[]{}));
        findById(id);
        roleRepository.delete(role);
    }

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