package com.mclods.secured_apis.dtos.request.create.ingredient;

import com.mclods.secured_apis.dtos.common.IngredientType;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCreateDto {
    @Pattern(
            regexp = "^[A-Z]{4}$",
            message = "Ingredient id must contain exactly 4 letters"
    )
    private String id;

    @Length(max = 50, message = "Ingredient name should not exceed 50 characters")
    private String name;

    private IngredientType type;
}
