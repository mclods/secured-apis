package com.mclods.secured_apis.services.impl;

import com.mclods.secured_apis.dtos.request.create.role.RoleCreateDto;
import com.mclods.secured_apis.entities.Role;
import com.mclods.secured_apis.repositories.RoleRepository;
import com.mclods.secured_apis.services.PermissionService;
import com.mclods.secured_apis.services.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionService permissionService;

    public RoleServiceImpl(RoleRepository roleRepository, PermissionService permissionService) {
        this.roleRepository = roleRepository;
        this.permissionService = permissionService;
    }

    @Override
    public Role createRole(RoleCreateDto roleCreateDto) {
        var roleToCreate = new Role();

        roleToCreate.setName(roleCreateDto.getName());
        roleToCreate.setDescription(roleCreateDto.getDescription());

        if(roleCreateDto.getPermissions() != null) {
            var rolePermissions = roleCreateDto.getPermissions()
                    .stream()
                    .map(pDto -> permissionService.findPermissionById(pDto.getId()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());

            roleToCreate.setPermissions(rolePermissions);
        }

        var savedRole = roleRepository.save(roleToCreate);
        log.info("Role created with id: {}, name: {}", savedRole.getId(), savedRole.getName());

        return savedRole;
    }

    @Override
    public List<Role> createRoles(List<RoleCreateDto> roleCreateDtoList) {
        return roleCreateDtoList.stream().map(this::createRole).toList();
    }

    @Override
    public List<Role> findAllRoles() {
        List<Role> roles = new ArrayList<>();
        roleRepository.findAll().forEach(roles::add);

        return roles;
    }

    @Override
    public Optional<Role> findRoleById(Integer id) {
        var foundRole = roleRepository.findById(id);

        if(foundRole.isEmpty()) {
            log.warn("Role with id: {} not found", id);
        }

        return foundRole;
    }

    @Override
    public void deleteRoleById(Integer id) {
        roleRepository.deleteById(id);
        log.info("Role with id: {} deleted", id);
    }
}
