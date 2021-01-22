package com.kamila.food.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.kamila.food.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> find(String nmRestaurante, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

	List<Restaurante> findComFreteGratis(String nmRestaurante);
	
}