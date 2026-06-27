package com.mclods.secured_apis.mappers;

import com.mclods.secured_apis.dtos.response.user.UserDto;
import com.mclods.secured_apis.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserDto map(User user);
}
