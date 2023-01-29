package com.pai.project.repository;

import com.pai.project.entity.Fee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FeeRepository extends CrudRepository<Fee, Long> {

    List<Fee> findAllByLogin(String login);
}
