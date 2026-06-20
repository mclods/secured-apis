package com.mclods.secured_apis.dtos.response.taco;

import com.mclods.secured_apis.dtos.response.ingredient.IngredientDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class TacoDto {
    private Integer id;

    private String name;

    private LocalDateTime creationDate;

    private Set<IngredientDto> ingredients;
}
