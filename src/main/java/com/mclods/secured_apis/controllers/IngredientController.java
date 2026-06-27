package com.mclods.secured_apis.controllers;

import com.mclods.secured_apis.dtos.request.ingredient.create.IngredientCreateDto;
import com.mclods.secured_apis.dtos.response.ingredient.IngredientDto;
import com.mclods.secured_apis.mappers.IngredientMapper;
import com.mclods.secured_apis.services.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('READ_INGREDIENT')")
    public ResponseEntity<IngredientDto> findIngredientById(@PathVariable String id) {
        var ingredient = ingredientService.findIngredientById(id);

        return ingredient.map(val -> new ResponseEntity<>(ingredientMapper.map(val), HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('READ_INGREDIENT')")
    public List<IngredientDto> findAllIngredients() {
        return ingredientService.findAllIngredients()
                .stream()
                .map(ingredientMapper::map)
                .toList();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('CREATE_INGREDIENT')")
    public IngredientDto createIngredient(@RequestBody IngredientCreateDto ingredientCreateDto) {
        return ingredientMapper.map(ingredientService.createIngredient(ingredientCreateDto));
    }

    @PostMapping(value = "bulk", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('CREATE_INGREDIENT')")
    public List<IngredientDto> createIngredients(@RequestBody List<IngredientCreateDto> ingredientCreateDtoList) {
        return ingredientService.createIngredients(ingredientCreateDtoList)
                .stream()
                .map(ingredientMapper::map)
                .toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('DELETE_INGREDIENT')")
    public void deleteIngredientById(@PathVariable String id) {
        ingredientService.deleteIngredientById(id);
    }
}
