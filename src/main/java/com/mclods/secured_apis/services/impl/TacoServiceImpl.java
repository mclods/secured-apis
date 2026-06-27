package com.mclods.secured_apis.services.impl;

import com.mclods.secured_apis.dtos.request.taco.create.TacoCreateDto;
import com.mclods.secured_apis.dtos.request.taco.update.full.TacoFullUpdateDto;
import com.mclods.secured_apis.dtos.request.taco.update.partial.TacoPartialUpdateDto;
import com.mclods.secured_apis.entities.Taco;
import com.mclods.secured_apis.exceptions.TacoNotFoundException;
import com.mclods.secured_apis.repositories.TacoRepository;
import com.mclods.secured_apis.services.IngredientService;
import com.mclods.secured_apis.services.TacoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TacoServiceImpl implements TacoService {
    private final TacoRepository tacoRepository;
    private final IngredientService ingredientService;

    public TacoServiceImpl(TacoRepository tacoRepository, IngredientService ingredientService) {
        this.tacoRepository = tacoRepository;
        this.ingredientService = ingredientService;
    }

    @Override
    public Optional<Taco> findTacoById(Integer id) {
        var foundTaco = tacoRepository.findById(id);

        if(foundTaco.isEmpty()) {
            log.warn("Taco with id: {} not found", id);
        }

        return foundTaco;
    }

    @Override
    public List<Taco> findAllTacos() {
        List<Taco> tacos = new ArrayList<>();
        tacoRepository.findAll().forEach(tacos::add);

        return tacos;
    }

    @Override
    public Taco createTaco(TacoCreateDto tacoCreateDto) {
        var tacoToCreate = new Taco();

        tacoToCreate.setName(tacoCreateDto.getName());

        if(tacoCreateDto.getCreationDate() != null) {
            tacoToCreate.setCreationDate(tacoCreateDto.getCreationDate());
        } else  {
            tacoToCreate.setCreationDate(LocalDateTime.now());
        }

        if(tacoCreateDto.getIngredients() != null) {
            var tacoIngredients = tacoCreateDto.getIngredients()
                    .stream()
                    .map(tiDto ->  ingredientService.findIngredientById(tiDto.getId()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());

            tacoToCreate.setIngredients(tacoIngredients);
        }

        var savedTaco = tacoRepository.save(tacoToCreate);
        log.info("Taco created with id: {}, name: {}", savedTaco.getId(), tacoToCreate.getName());

        return savedTaco;
    }

    @Override
    public Taco partialUpdateTaco(Integer id, TacoPartialUpdateDto tacoPartialUpdateDto) throws TacoNotFoundException {
        var tacoToUpdate = findTacoById(id).orElseThrow(() -> new TacoNotFoundException(id));

        Optional.ofNullable(tacoPartialUpdateDto.getName()).ifPresent(tacoToUpdate::setName);
        tacoToUpdate.setCreationDate(LocalDateTime.now());

        if(tacoPartialUpdateDto.getIngredients() != null) {
            var tacoIngredients = tacoPartialUpdateDto.getIngredients()
                    .stream()
                    .map(tiDto ->  ingredientService.findIngredientById(tiDto.getId()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());

            tacoToUpdate.getIngredients().addAll(tacoIngredients);
        }

        var updatedTaco = tacoRepository.save(tacoToUpdate);
        log.info("Taco partially updated with id: {}, name: {}", updatedTaco.getId(), updatedTaco.getName());

        return updatedTaco;
    }

    @Override
    public Taco fullUpdateTaco(Integer id, TacoFullUpdateDto tacoFullUpdateDto) throws TacoNotFoundException {
        var tacoToUpdate = findTacoById(id).orElseThrow(() -> new TacoNotFoundException(id));

        tacoToUpdate.setName(tacoFullUpdateDto.getName());
        tacoToUpdate.setCreationDate(LocalDateTime.now());

        if(tacoFullUpdateDto.getIngredients() != null) {
            var tacoIngredients = tacoFullUpdateDto.getIngredients()
                    .stream()
                    .map(tiDto ->  ingredientService.findIngredientById(tiDto.getId()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());

            tacoToUpdate.setIngredients(tacoIngredients);
        }

        var updatedTaco = tacoRepository.save(tacoToUpdate);
        log.info("Taco fully updated with id: {}, name: {}", updatedTaco.getId(), updatedTaco.getName());

        return updatedTaco;
    }

    @Override
    public void deleteTacoById(Integer id) {
        tacoRepository.deleteById(id);
        log.info("Taco with id: {} deleted", id);
    }
}
