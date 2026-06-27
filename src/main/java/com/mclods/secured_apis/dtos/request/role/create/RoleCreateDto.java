package com.mclods.secured_apis.dtos.request.role.create;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleCreateDto {
    @Size(max = 50, message = "Role Name cannot exceed 50 characters")
    private String name;

    private String description;

    private Set<RolePermissionsCreateDto> permissions;
}
