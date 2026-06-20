package com.mclods.secured_apis.repositories;

import com.mclods.secured_apis.entities.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Integer> {
}
