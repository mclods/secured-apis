package com.mclods.secured_apis.repositories;

import com.mclods.secured_apis.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findRoleByName(String name);
}
