package com.mclods.secured_apis.services.impl;

import com.mclods.secured_apis.dtos.request.permission.create.PermissionCreateDto;
import com.mclods.secured_apis.entities.Permission;
import com.mclods.secured_apis.mappers.PermissionMapper;
import com.mclods.secured_apis.repositories.PermissionRepository;
import com.mclods.secured_apis.services.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    public PermissionServiceImpl(PermissionRepository permissionRepository, PermissionMapper permissionMapper) {
        this.permissionRepository = permissionRepository;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public Permission createPermission(PermissionCreateDto permissionCreateDto) {
        var permissionToCreate = permissionMapper.map(permissionCreateDto);
        var existingPermission = findPermissionByName(permissionCreateDto.getName());

        Permission savedPermission = null;
        if(existingPermission.isEmpty()) {
            savedPermission = permissionRepository.save(permissionToCreate);
            log.info("Permission created with id: {}, name: {}", savedPermission.getId(), savedPermission.getName());
        } else {
            savedPermission = existingPermission.get();
            log.info("Permission already exists with id: {}, name: {}", savedPermission.getId(), savedPermission.getName());
        }

        return savedPermission;
    }

    @Override
    public List<Permission> createPermissions(List<PermissionCreateDto> permissionCreateDtoList) {
        return permissionCreateDtoList.stream().map(this::createPermission).toList();
    }

    @Override
    public List<Permission> findAllPermissions() {
        List<Permission> permissions = new ArrayList<>();
        permissionRepository.findAll().forEach(permissions::add);

        return permissions;
    }

    @Override
    public Optional<Permission> findPermissionById(Integer id) {
        var foundPermission = permissionRepository.findById(id);

        if(foundPermission.isEmpty()) {
            log.warn("Permission with id: {} not found", id);
        }

        return foundPermission;
    }

    @Override
    public Optional<Permission> findPermissionByName(String name) {
        var foundPermission = permissionRepository.findPermissionByName(name);

        if(foundPermission.isEmpty()) {
            log.warn("Permission with name: {} not found", name);
        }

        return foundPermission;
    }

    @Override
    public void deletePermissionById(Integer id) {
        permissionRepository.deleteById(id);
        log.info("Permission with id: {} deleted", id);
    }
}
