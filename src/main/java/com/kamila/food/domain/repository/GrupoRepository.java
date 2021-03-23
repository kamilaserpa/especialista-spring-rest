package com.kamila.food.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kamila.food.domain.model.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

}
