package com.mclods.secured_apis.dtos.response.role;

import com.mclods.secured_apis.dtos.response.permission.PermissionDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RoleDto {
    private Integer id;

    private String name;

    private String description;

    private Set<PermissionDto> permissions;
}
