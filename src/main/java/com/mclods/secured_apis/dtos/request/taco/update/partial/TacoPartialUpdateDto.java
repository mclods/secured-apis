package com.mclods.secured_apis.dtos.request.taco.update.partial;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class TacoPartialUpdateDto {
    private Integer id;

    private String name;

    private LocalDateTime creationDate;

    private Set<TacoIngredientsPartialUpdateDto> ingredients;
}
