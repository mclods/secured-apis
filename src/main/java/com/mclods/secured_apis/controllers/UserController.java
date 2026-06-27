package com.mclods.secured_apis.controllers;

import com.mclods.secured_apis.dtos.request.user.create.UserCreateDto;
import com.mclods.secured_apis.dtos.request.user.update.UserChangePasswordDto;
import com.mclods.secured_apis.dtos.response.user.UserDto;
import com.mclods.secured_apis.exceptions.AppException;
import com.mclods.secured_apis.mappers.UserMapper;
import com.mclods.secured_apis.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = "application/json")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<UserDto> findUserById(@PathVariable Integer id) {
        var user = userService.findUserById(id);

        return user.map(u -> ResponseEntity.ok(userMapper.map(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('READ_USER')")
    public List<UserDto> findAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::map)
                .toList();
    }

    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('CREATE_USER')")
    public UserDto createUser(@RequestBody UserCreateDto userCreateDto) {
        return userMapper.map(userService.createUser(userCreateDto));
    }

    @PatchMapping(value = "/{id}/change-password")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('CHANGE_USER_PASSWORD')")
    public UserDto changePassword(
            @PathVariable Integer id,
            @RequestBody UserChangePasswordDto userChangePasswordDto
    ) throws AppException {
        return userMapper.map(userService.changePassword(id, userChangePasswordDto));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('DELETE_USER')")
    public void deleteUserById(@PathVariable Integer id) {
        userService.deleteUserById(id);
    }
}
