package com.mclods.secured_apis.controllers;

import com.mclods.secured_apis.dtos.request.create.ingredient.IngredientCreateDto;
import com.mclods.secured_apis.dtos.response.ingredient.IngredientDto;
import com.mclods.secured_apis.mappers.IngredientMapper;
import com.mclods.secured_apis.services.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ingredients", produces = "application/json")
public class IngredientController {
    private final IngredientService ingredientService;
    private final IngredientMapper ingredientMapper;

    public IngredientController(IngredientService ingredientService, IngredientMapper ingredientMapper) {
        this.ingredientService = ingredientService;
        this.ingredientMapper = ingredientMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientDto> findIngredientById(@PathVariable String id) {
        var ingredient = ingredientService.findIngredientById(id);

        return ingredient.map(val -> new ResponseEntity<>(ingredientMapper.map(val), HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<IngredientDto> findAllIngredients() {
        return ingredientService.findAllIngredients()
                .stream()
                .map(ingredientMapper::map)
                .toList();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public IngredientDto createIngredient(@RequestBody IngredientCreateDto ingredientCreateDto) {
        return ingredientMapper.map(ingredientService.createIngredient(ingredientCreateDto));
    }

    @PostMapping(value = "bulk", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public List<IngredientDto> createIngredients(@RequestBody List<IngredientCreateDto> ingredientCreateDtoList) {
        return ingredientService.createIngredients(ingredientCreateDtoList)
                .stream()
                .map(ingredientMapper::map)
                .toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIngredientById(@PathVariable String id) {
        ingredientService.deleteIngredientById(id);
    }
}
