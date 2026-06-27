package com.mclods.secured_apis.dtos.request.user.update;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserChangePasswordDto {
    private Integer id;

    private String oldPassword;

    private String newPassword;
}
