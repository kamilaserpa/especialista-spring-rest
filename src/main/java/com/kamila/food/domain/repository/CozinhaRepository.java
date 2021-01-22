package com.kamila.food.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.kamila.food.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long>{

	List<Cozinha> findTodasByNmCozinhaContaining(String nmCozinha);
	
	Optional<Cozinha> findByNmCozinha(String nmCozinha); // Espera que só exista uma

	boolean existsByNmCozinha(String nmCozinha); // Nome exato

}
