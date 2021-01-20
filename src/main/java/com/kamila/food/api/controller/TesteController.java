package com.kamila.food.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kamila.food.domain.model.Cozinha;
import com.kamila.food.domain.model.Restaurante;
import com.kamila.food.domain.repository.CozinhaRepository;
import com.kamila.food.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private RestauranteRepository restauranteaRepository;
	
	@GetMapping("/cozinhas/por-nome")
	public List<Cozinha> cozinhasPorNome(@RequestParam("nmCozinha") String nmCozinha) {
		return cozinhaRepository.findTodasByNmCozinhaContaining(nmCozinha);
	}
	
	@GetMapping("/cozinhas/unica-por-nome")
	public Optional<Cozinha> cozinhaPorNome(@RequestParam("nmCozinha") String nmCozinha) {
		return cozinhaRepository.findByNmCozinha(nmCozinha);
	}
	
	@GetMapping("/restaurantes/por-taxa-frete")
	public List<Restaurante> restaurantesPorTaxa(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteaRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
	}
	
	@GetMapping("/restaurantes/por-nome")
	public List<Restaurante> restaurantesPorNomeCozinha(String nmRestaurante, Long idCozinha) {
		return restauranteaRepository.findByNmRestauranteContainingAndCozinhaId(nmRestaurante, idCozinha);
	}
	
}