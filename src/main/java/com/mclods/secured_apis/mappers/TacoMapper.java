package com.mclods.secured_apis.mappers;

import com.mclods.secured_apis.dtos.response.taco.TacoDto;
import com.mclods.secured_apis.entities.Taco;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TacoMapper {
    TacoDto map(Taco taco);
}
