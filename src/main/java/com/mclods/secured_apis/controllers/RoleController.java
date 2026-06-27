package com.mclods.secured_apis.controllers;

import com.mclods.secured_apis.dtos.request.role.create.RoleCreateDto;
import com.mclods.secured_apis.dtos.response.role.RoleDto;
import com.mclods.secured_apis.mappers.RoleMapper;
import com.mclods.secured_apis.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/roles", produces = "application/json")
public class RoleController {
    private final RoleService roleService;
    private final RoleMapper roleMapper;

    public RoleController(RoleService roleService, RoleMapper roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ROLE')")
    public ResponseEntity<RoleDto> findRoleById(@PathVariable Integer id) {
        var role = roleService.findRoleById(id);

        return role.map(r -> ResponseEntity.ok(roleMapper.map(r)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ROLE')")
    public List<RoleDto> findAllRoles() {
        return roleService.findAllRoles()
                .stream()
                .map(roleMapper::map)
                .toList();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    public RoleDto createRole(RoleCreateDto roleCreateDto) {
        return roleMapper.map(roleService.createRole(roleCreateDto));
    }

    @PostMapping(value = "bulk", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    public List<RoleDto> createRoles(@RequestBody List<RoleCreateDto> roleCreateDtos) {
        return roleService.createRoles(roleCreateDtos)
                .stream()
                .map(roleMapper::map)
                .toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('DELETE_ROLE')")
    public void deleteRoleById(@PathVariable Integer id) {
        roleService.deleteRoleById(id);
    }
}
