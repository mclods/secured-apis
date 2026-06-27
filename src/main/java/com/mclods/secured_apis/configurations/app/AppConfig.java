package com.mclods.secured_apis.configurations.app;

import com.mclods.secured_apis.dtos.request.permission.create.PermissionCreateDto;
import com.mclods.secured_apis.dtos.request.role.create.RoleCreateDto;
import com.mclods.secured_apis.dtos.request.role.create.RolePermissionsCreateDto;
import com.mclods.secured_apis.dtos.request.user.create.UserCreateDto;
import com.mclods.secured_apis.dtos.request.user.create.UserRolesCreateDto;
import com.mclods.secured_apis.services.PermissionService;
import com.mclods.secured_apis.services.RoleService;
import com.mclods.secured_apis.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class AppConfig {
    @Bean
    public CommandLineRunner commandLineRunner(
            PermissionService permissionService,
            RoleService roleService,
            UserService userService
    ) {
        return args -> {
            var role1 = new PermissionCreateDto("CREATE_USER", "Ability to create user");
            var role2 = new PermissionCreateDto("READ_USER", "Ability to read user");
            var role3 = new PermissionCreateDto("CREATE_PERMISSION", "Ability to create permission");
            var role4 = new PermissionCreateDto("READ_PERMISSION", "Ability to read permission");
            var role5 = new PermissionCreateDto("CREATE_ROLE", "Ability to create role");
            var role6 = new PermissionCreateDto("READ_ROLE", "Ability to read role");

            permissionService.createPermission(role1);
            permissionService.createPermission(role2);
            permissionService.createPermission(role3);
            permissionService.createPermission(role4);
            permissionService.createPermission(role5);
            permissionService.createPermission(role6);


            var role = new RoleCreateDto(
                    "ROLE_INSTALLATION_USER",
                    "Installation User Role",
                    Set.of(
                            new RolePermissionsCreateDto("CREATE_USER"),
                            new RolePermissionsCreateDto("READ_USER"),
                            new RolePermissionsCreateDto("CREATE_PERMISSION"),
                            new RolePermissionsCreateDto("READ_PERMISSION"),
                            new RolePermissionsCreateDto("CREATE_ROLE"),
                            new RolePermissionsCreateDto("READ_ROLE")
                    )
            );
            var savedRole = roleService.createRole(role);

            var user = new UserCreateDto(
                    "admin",
                    "admin",
                    Set.of(new UserRolesCreateDto(savedRole.getId()))
            );
            userService.createUser(user);
        };
    }
}
