package com.mclods.secured_apis.repositories;

import com.mclods.secured_apis.entities.Permission;
import org.springframework.data.repository.CrudRepository;

public interface PermissionRepository extends CrudRepository<Permission, Integer> {
}
