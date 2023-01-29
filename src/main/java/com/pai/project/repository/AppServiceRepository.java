package com.pai.project.repository;

import com.pai.project.entity.AppService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppServiceRepository extends CrudRepository<AppService, Long> {

    AppService findByName(String name);
}
