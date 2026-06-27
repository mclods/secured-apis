package com.mclods.secured_apis.services.impl;

import com.mclods.secured_apis.dtos.request.user.create.UserCreateDto;
import com.mclods.secured_apis.dtos.request.user.update.UserChangePasswordDto;
import com.mclods.secured_apis.entities.User;
import com.mclods.secured_apis.exceptions.AppException;
import com.mclods.secured_apis.exceptions.UserNotFoundException;
import com.mclods.secured_apis.repositories.UserRepository;
import com.mclods.secured_apis.services.RoleService;
import com.mclods.secured_apis.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(UserCreateDto userCreateDto) {
        var userToCreate = new User();

        userToCreate.setUsername(userCreateDto.getUsername());
        userToCreate.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));

        if(userCreateDto.getRoles() != null) {
            var userRoles = userCreateDto.getRoles()
                    .stream()
                    .map(urDto -> {
                        if(urDto.getId() != null) {
                            return roleService.findRoleById(urDto.getId());
                        } else {
                            return roleService.findRoleByName(urDto.getName());
                        }
                    })
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
    public User changePassword(Integer id, UserChangePasswordDto userChangePasswordDto) throws AppException {
        var userToUpdate = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        String oldPassword = userChangePasswordDto.getOldPassword();
        String newPassword = userChangePasswordDto.getNewPassword();
        if(!oldPassword.equals(newPassword)) {
            if(passwordEncoder.matches(oldPassword, userToUpdate.getPassword())) {
                userToUpdate.setPassword(passwordEncoder.encode(newPassword));

                var savedUser = userRepository.save(userToUpdate);
                log.info("User with id: {}, username: {}, password updated", savedUser.getId(), savedUser.getUsername());

                return savedUser;
            } else {
                throw new AppException("Old Password is wrong. Change not allowed.");
            }
        } else {
            throw new AppException("Old and New Passwords are same. Choose a different one.");
        }
    }

    @Override
    public Optional<User> findUserById(Integer id) {
        var foundUser = userRepository.findById(id);

        if(foundUser.isEmpty()) {
            log.warn("User with id: {} not found", id);
        }

        return foundUser;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> foundUsers = new ArrayList<>();
        userRepository.findAll().forEach(foundUsers::add);

        return foundUsers;
    }

    @Override
    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
        log.info("User with id: {} deleted", id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsername(username);

        return user.orElseThrow(() -> new UsernameNotFoundException("User: %s not found".formatted(username)));
    }
}
