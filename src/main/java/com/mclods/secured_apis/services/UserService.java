package com.mclods.secured_apis.services;

import com.mclods.secured_apis.dtos.request.user.create.UserCreateDto;
import com.mclods.secured_apis.dtos.request.user.update.UserChangePasswordDto;
import com.mclods.secured_apis.entities.User;
import com.mclods.secured_apis.exceptions.AppException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    User createUser(UserCreateDto userCreateDto);

    User changePassword(Integer id, UserChangePasswordDto userChangePasswordDto) throws AppException;

    Optional<User> findUserById(Integer id);

    List<User> findAllUsers();

    void deleteUserById(Integer id);
}
