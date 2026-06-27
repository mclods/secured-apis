package com.mclods.secured_apis.controllers;

import com.mclods.secured_apis.dtos.request.taco.create.TacoCreateDto;
import com.mclods.secured_apis.dtos.request.taco.update.full.TacoFullUpdateDto;
import com.mclods.secured_apis.dtos.request.taco.update.partial.TacoPartialUpdateDto;
import com.mclods.secured_apis.dtos.response.taco.TacoDto;
import com.mclods.secured_apis.exceptions.TacoNotFoundException;
import com.mclods.secured_apis.mappers.TacoMapper;
import com.mclods.secured_apis.services.TacoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tacos", produces = "application/json")
public class TacoController {
    private final TacoService tacoService;
    private final TacoMapper tacoMapper;

    public TacoController(TacoService tacoService, TacoMapper tacoMapper) {
        this.tacoService = tacoService;
        this.tacoMapper = tacoMapper;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_TACO')")
    public ResponseEntity<TacoDto> findTacoById(@PathVariable Integer id) {
        var taco = tacoService.findTacoById(id);

        return taco.map(t -> ResponseEntity.ok(tacoMapper.map(t)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('READ_TACO')")
    public List<TacoDto> findAllTacos() {
        return tacoService.findAllTacos()
                .stream()
                .map(tacoMapper::map)
                .toList();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('CREATE_TACO')")
    public TacoDto createTaco(@RequestBody @Valid TacoCreateDto tacoCreateDto) {
        return tacoMapper.map(tacoService.createTaco(tacoCreateDto));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json")
    @PreAuthorize("hasAuthority('UPDATE_TACO')")
    public ResponseEntity<TacoDto> partialUpdateTaco(@PathVariable Integer id, @RequestBody @Valid TacoPartialUpdateDto tacoPartialUpdateDto) {
        try {
            var updatedTaco = tacoService.partialUpdateTaco(id, tacoPartialUpdateDto);
            return new ResponseEntity<>(tacoMapper.map(updatedTaco), HttpStatus.OK);
        } catch (TacoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping(path = "/{id}", consumes = "application/json")
    @PreAuthorize("hasAuthority('UPDATE_TACO')")
    public ResponseEntity<TacoDto> fullUpdateTaco(@PathVariable Integer id, @RequestBody @Valid TacoFullUpdateDto tacoFullUpdateDto) {
        try {
            var updatedTaco = tacoService.fullUpdateTaco(id, tacoFullUpdateDto);
            return new ResponseEntity<>(tacoMapper.map(updatedTaco), HttpStatus.OK);
        } catch (TacoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('DELETE_TACO')")
    public void deleteTacoById(@PathVariable Integer id) {
        tacoService.deleteTacoById(id);
    }
}
