package com.mclods.secured_apis.services;

import com.mclods.secured_apis.dtos.request.create.taco.TacoCreateDto;
import com.mclods.secured_apis.dtos.request.update.full.taco.TacoFullUpdateDto;
import com.mclods.secured_apis.dtos.request.update.partial.taco.TacoPartialUpdateDto;
import com.mclods.secured_apis.entities.Taco;
import com.mclods.secured_apis.exceptions.TacoNotFoundException;

import java.util.List;
import java.util.Optional;

public interface TacoService {
    Optional<Taco> findTacoById(Integer id);

    List<Taco> findAllTacos();

    Taco createTaco(TacoCreateDto tacoCreateDto);

    Taco partialUpdateTaco(Integer id, TacoPartialUpdateDto tacoPartialUpdateDto) throws TacoNotFoundException;

    Taco fullUpdateTaco(Integer id, TacoFullUpdateDto tacoFullUpdateDto) throws TacoNotFoundException;

    void deleteTacoById(Integer id);
}
