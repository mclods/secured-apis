package com.mclods.secured_apis.services;

import com.mclods.secured_apis.dtos.request.ingredient.create.IngredientCreateDto;
import com.mclods.secured_apis.entities.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientService {
    Ingredient createIngredient(IngredientCreateDto ingredientCreateDto);

    List<Ingredient> createIngredients(List<IngredientCreateDto> ingredientCreateDtoList);

    Optional<Ingredient> findIngredientById(String id);

    List<Ingredient> findAllIngredients();

    void deleteIngredientById(String id);
}
