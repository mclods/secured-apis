package com.mclods.secured_apis.dtos.request.taco.update.full;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TacoIngredientsFullUpdateDto {
    @Pattern(
            regexp = "^[A-Z]{4}$",
            message = "Ingredient id must contain exactly 4 letters"
    )
    private String id;
}
