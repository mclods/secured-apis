package com.mclods.secured_apis.dtos.request.user.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRolesCreateDto {
    Integer id;

    String name;

    public UserRolesCreateDto(Integer id) {
        this.id = id;
    }

    public UserRolesCreateDto(String name) {
        this.name = name;
    }
}
