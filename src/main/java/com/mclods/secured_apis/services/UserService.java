package com.mclods.secured_apis.services;

import com.mclods.secured_apis.dtos.request.create.user.UserCreateDto;
import com.mclods.secured_apis.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User createUser(UserCreateDto userCreateDto);
}
