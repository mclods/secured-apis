package com.mclods.secured_apis.dtos.request.create.permission;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionCreateDto {
    @Size(max = 50, message = "Permission Name cannot exceed 50 characters")
    private String name;

    private String description;
}
