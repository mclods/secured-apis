package com.mclods.secured_apis.dtos.response.ingredient;

import com.mclods.secured_apis.dtos.common.IngredientType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IngredientDto {
    private String id;

    private String name;

    private IngredientType type;
}
