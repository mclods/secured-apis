package com.mclods.secured_apis.dtos.request.create.user;

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
public class UserCreateDto {
    @Size(max = 50, message = "Username should not exceed 50 characters")
    private String username;

    private String password;

    private Set<UserRolesCreateDto> roles;
}
