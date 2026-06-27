package com.mclods.secured_apis.mappers;

import com.mclods.secured_apis.dtos.common.IngredientType;
import com.mclods.secured_apis.dtos.request.ingredient.create.IngredientCreateDto;
import com.mclods.secured_apis.dtos.response.ingredient.IngredientDto;
import com.mclods.secured_apis.entities.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class IngredientMapper {
    public abstract Ingredient map(IngredientCreateDto ingredientCreateDto);

    public abstract IngredientDto map(Ingredient ingredient);

    public Ingredient.Type convert(IngredientType ingredientType) {
        return switch (ingredientType) {
            case WRAP -> Ingredient.Type.WRAP;
            case PROTEIN -> Ingredient.Type.PROTEIN;
            case VEGGIES -> Ingredient.Type.VEGGIES;
            case CHEESE -> Ingredient.Type.CHEESE;
            case SAUCE -> Ingredient.Type.SAUCE;
        };
    }
}
