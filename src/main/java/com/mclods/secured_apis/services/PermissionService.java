package com.mclods.secured_apis.services;

import com.mclods.secured_apis.dtos.request.permission.create.PermissionCreateDto;
import com.mclods.secured_apis.entities.Permission;

import java.util.List;
import java.util.Optional;

public interface PermissionService {
    Permission createPermission(PermissionCreateDto permissionCreateDto);

    List<Permission> createPermissions(List<PermissionCreateDto> permissionCreateDtoList);

    List<Permission> findAllPermissions();

    Optional<Permission> findPermissionById(Integer id);

    Optional<Permission> findPermissionByName(String name);

    void deletePermissionById(Integer id);
}
