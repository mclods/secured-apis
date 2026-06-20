package com.mclods.secured_apis.configurations.app;

import com.mclods.secured_apis.dtos.request.create.permission.PermissionCreateDto;
import com.mclods.secured_apis.dtos.request.create.role.RoleCreateDto;
import com.mclods.secured_apis.dtos.request.create.role.RolePermissionsCreateDto;
import com.mclods.secured_apis.dtos.request.create.user.UserCreateDto;
import com.mclods.secured_apis.dtos.request.create.user.UserRolesCreateDto;
import com.mclods.secured_apis.services.PermissionService;
import com.mclods.secured_apis.services.RoleService;
import com.mclods.secured_apis.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class AppConfig {
    @Bean
    public CommandLineRunner commandLineRunner(
            PermissionService permissionService,
            RoleService roleService,
            UserService userService,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            var permission1 = new PermissionCreateDto("EDIT_TACO", "Ability to edit taco");
            var permission2 = new PermissionCreateDto("READ_TACO", "Ability to read taco");
            var savedPermission1 = permissionService.createPermission(permission1);
            var savedPermission2 = permissionService.createPermission(permission2);

            var role = new RoleCreateDto(
                    "ROLE_ADMIN",
                    "Admin Role",
                    Set.of(
                            new RolePermissionsCreateDto(savedPermission1.getId()),
                            new RolePermissionsCreateDto(savedPermission2.getId())
                    )
            );
            var savedRole = roleService.createRole(role);

            var user = new UserCreateDto(
                    "admin",
                    passwordEncoder.encode("admin"),
                    Set.of(new UserRolesCreateDto(savedRole.getId()))
            );
            userService.createUser(user);
        };
    }
}
