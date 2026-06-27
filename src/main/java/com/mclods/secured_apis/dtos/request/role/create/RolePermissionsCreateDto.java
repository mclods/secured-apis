package com.mclods.secured_apis.dtos.request.role.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionsCreateDto {
    Integer id;

    String name;

    public RolePermissionsCreateDto(Integer id) {
        this.id = id;
    }

    public RolePermissionsCreateDto(String name) {
        this.name = name;
    }
}
