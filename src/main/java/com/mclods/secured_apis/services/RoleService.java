package com.mclods.secured_apis.services;

import com.mclods.secured_apis.dtos.request.create.role.RoleCreateDto;
import com.mclods.secured_apis.entities.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role createRole(RoleCreateDto roleCreateDto);

    List<Role> createRoles(List<RoleCreateDto> roleCreateDtoList);

    List<Role> findAllRoles();

    Optional<Role> findRoleById(Integer id);

    void deleteRoleById(Integer id);
}
