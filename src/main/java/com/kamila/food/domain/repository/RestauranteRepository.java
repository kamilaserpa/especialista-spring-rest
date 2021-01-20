package com.kamila.food.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kamila.food.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

	List<Restaurante> queryByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

	@Query("from Restaurante where nmRestaurante like %:nmRestaurante% and cozinha.id = :idCozinha")
	List<Restaurante> consultarPorNome(String nmRestaurante, Long idCozinha);
	
//	List<Restaurante> findByNmRestauranteContainingAndCozinhaId(String nmRestaurante, Long idCozinha);
	
	Optional<Restaurante> findFirstQualquerDescricaoByNmRestauranteContaining(String nmRestaurante);
	
	List<Restaurante> findTop2ByNmRestauranteContaining(String nmRestaurante);
	
	int countByCozinhaId(Long idCozinha);
	
}
