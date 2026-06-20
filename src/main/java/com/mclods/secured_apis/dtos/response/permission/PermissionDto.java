package com.mclods.secured_apis.dtos.response.permission;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PermissionDto {
    private Integer id;

    private String name;

    private String description;
}
