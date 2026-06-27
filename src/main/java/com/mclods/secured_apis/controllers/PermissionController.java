package com.mclods.secured_apis.controllers;

import com.mclods.secured_apis.dtos.request.permission.create.PermissionCreateDto;
import com.mclods.secured_apis.dtos.response.permission.PermissionDto;
import com.mclods.secured_apis.mappers.PermissionMapper;
import com.mclods.secured_apis.services.PermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/permissions", produces = "application/json")
public class PermissionController {
    private final PermissionService permissionService;
    private final PermissionMapper permissionMapper;

    public PermissionController(PermissionService permissionService, PermissionMapper permissionMapper) {
        this.permissionService = permissionService;
        this.permissionMapper = permissionMapper;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_PERMISSION')")
    public ResponseEntity<PermissionDto> findPermissionById(@PathVariable Integer id) {
        var permission = permissionService.findPermissionById(id);

        return permission.map(p -> ResponseEntity.ok(permissionMapper.map(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('READ_PERMISSION')")
    public List<PermissionDto> findAllPermissions() {
        return permissionService.findAllPermissions()
                .stream()
                .map(permissionMapper::map)
                .toList();
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAuthority('CREATE_PERMISSION')")
    @ResponseStatus(HttpStatus.CREATED)
    public PermissionDto createPermission(@RequestBody PermissionCreateDto permissionCreateDto) {
        return permissionMapper.map(permissionService.createPermission(permissionCreateDto));
    }

    @PostMapping(value = "/bulk", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('CREATE_PERMISSION')")
    public List<PermissionDto> createPermissions(@RequestBody List<PermissionCreateDto> permissionCreateDtoList) {
        return permissionService.createPermissions(permissionCreateDtoList)
                .stream()
                .map(permissionMapper::map)
                .toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('DELETE_PERMISSION')")
    public void deletePermissionById(@PathVariable Integer id) {
        permissionService.deletePermissionById(id);
    }
}
