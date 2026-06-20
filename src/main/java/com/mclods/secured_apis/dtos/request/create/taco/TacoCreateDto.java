package com.mclods.secured_apis.dtos.request.create.taco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class TacoCreateDto {
    @Size(max = 50, message = "Taco name cannot exceed 50 characters")
    @NotBlank(message = "Taco Name cannot be blank")
    private String name;

    private LocalDateTime creationDate;

    @Size(min = 1, message = "Taco should have at least one ingredient")
    private Set<TacoIngredientsCreateDto> ingredients;
}
