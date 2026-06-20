package com.mclods.secured_apis.services.impl;

import com.mclods.secured_apis.dtos.request.create.user.UserCreateDto;
import com.mclods.secured_apis.entities.User;
import com.mclods.secured_apis.repositories.UserRepository;
import com.mclods.secured_apis.services.RoleService;
import com.mclods.secured_apis.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public User createUser(UserCreateDto userCreateDto) {
        var userToCreate = new User();

        userToCreate.setUsername(userCreateDto.getUsername());
        userToCreate.setPassword(userCreateDto.getPassword());

        if(userCreateDto.getRoles() != null) {
            var userRoles = userCreateDto.getRoles()
                    .stream()
                    .map(urDto -> roleService.findRoleById(urDto.getId()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());

            userToCreate.setRoles(userRoles);
        }

        var savedUser = userRepository.save(userToCreate);
        log.info("User created with id: {}, username: {}", savedUser.getId(), savedUser.getUsername());

        return savedUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsername(username);

        return user.orElseThrow(() -> new UsernameNotFoundException("User: %s not found".formatted(username)));
    }
}
