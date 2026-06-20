package com.mclods.secured_apis.mappers;

import com.mclods.secured_apis.dtos.response.role.RoleDto;
import com.mclods.secured_apis.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {
    RoleDto map(Role role);
}
