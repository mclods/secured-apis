package com.mclods.secured_apis.repositories;

import com.mclods.secured_apis.entities.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
