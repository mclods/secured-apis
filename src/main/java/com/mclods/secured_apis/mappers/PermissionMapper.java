package com.mclods.secured_apis.mappers;

import com.mclods.secured_apis.dtos.request.permission.create.PermissionCreateDto;
import com.mclods.secured_apis.dtos.response.permission.PermissionDto;
import com.mclods.secured_apis.entities.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PermissionMapper {
    Permission map(PermissionCreateDto permissionCreateDto);

    PermissionDto map(Permission permission);
}
