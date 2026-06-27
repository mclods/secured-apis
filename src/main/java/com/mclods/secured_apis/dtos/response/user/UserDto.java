package com.mclods.secured_apis.dtos.response.user;

import com.mclods.secured_apis.dtos.response.role.RoleDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Integer id;

    private String username;

    private Set<RoleDto> roles;
}
