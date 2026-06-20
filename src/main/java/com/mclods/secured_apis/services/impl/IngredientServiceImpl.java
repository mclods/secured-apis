package com.mclods.secured_apis.services.impl;

import com.mclods.secured_apis.dtos.request.create.ingredient.IngredientCreateDto;
import com.mclods.secured_apis.entities.Ingredient;
import com.mclods.secured_apis.mappers.IngredientMapper;
import com.mclods.secured_apis.repositories.IngredientRepository;
import com.mclods.secured_apis.services.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientMapper ingredientMapper) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
    }

    @Override
    public Ingredient createIngredient(IngredientCreateDto ingredientCreateDto) {
        var ingredientToCreate = ingredientMapper.map(ingredientCreateDto);

        var savedIngredient = ingredientRepository.save(ingredientToCreate);
        log.info("Ingredient created with id: {}, name: {}", savedIngredient.getId(), savedIngredient.getName());

        return savedIngredient;
    }

    @Override
    public List<Ingredient> createIngredients(List<IngredientCreateDto> ingredientCreateDtoList) {
        return ingredientCreateDtoList
                .stream()
                .map(this::createIngredient)
                .toList();
    }

    @Override
    public Optional<Ingredient> findIngredientById(String id) {
        var foundIngredient = ingredientRepository.findById(id);

        if(foundIngredient.isEmpty()) {
            log.warn("Ingredient with id: {} not found", id);
        }

        return foundIngredient;
    }

    @Override
    public List<Ingredient> findAllIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredients::add);

        return ingredients;
    }

    @Override
    public void deleteIngredientById(String id) {
        ingredientRepository.deleteById(id);
        log.info("Ingredient with id: {} deleted", id);
    }
}
