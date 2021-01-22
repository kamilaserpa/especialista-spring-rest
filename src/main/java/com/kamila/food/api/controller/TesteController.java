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
	private RestauranteRepository restauranteRepository;
	
	@GetMapping("/cozinhas/por-nome")
	public List<Cozinha> cozinhasPorNome(@RequestParam("nmCozinha") String nmCozinha) {
		return cozinhaRepository.findTodasByNmCozinhaContaining(nmCozinha);
	}
	@GetMapping("/cozinhas/exists")
	public boolean cozinhasExistePorNome(@RequestParam("nmCozinha") String nmCozinha) {
		return cozinhaRepository.existsByNmCozinha(nmCozinha);
	}
	
	@GetMapping("/cozinhas/unica-por-nome")
	public Optional<Cozinha> cozinhaPorNome(@RequestParam("nmCozinha") String nmCozinha) {
		return cozinhaRepository.findByNmCozinha(nmCozinha);
	}
	
	@GetMapping("/restaurantes/por-taxa-frete")
	public List<Restaurante> restaurantesPorTaxa(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.queryByTaxaFreteBetween(taxaInicial, taxaFinal);
	}
	
	@GetMapping("/restaurantes/por-nome")
	public List<Restaurante> restaurantesPorNomeCozinha(String nmRestaurante, Long idCozinha) {
		return restauranteRepository.consultarPorNome(nmRestaurante, idCozinha);
	}
	
	@GetMapping("/restaurantes/por-nome-e-frete")
	public List<Restaurante> restaurantesPorNomeFrete(String nmRestaurante, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		return restauranteRepository.find(nmRestaurante, taxaFreteInicial, taxaFreteFinal);
	}
	
	@GetMapping("/restaurantes/primeiro-por-nome")
	public Optional<Restaurante> restaurantesPrimeiroPorNome(String nmRestaurante) {
		return restauranteRepository.findFirstQualquerDescricaoByNmRestauranteContaining(nmRestaurante);
	}
	
	@GetMapping("/restaurantes/top2-por-nome")
	public List<Restaurante> restaurantesTop2PorNome(String nmRestaurante) {
		return restauranteRepository.findTop2ByNmRestauranteContaining(nmRestaurante);
	}
	
	@GetMapping("/restaurantes/count-por-cozinha")
	public int restaurantesCountPorCozinha(Long idCozinha) {
		return restauranteRepository.countByCozinhaId(idCozinha);
	}
	
	@GetMapping("/restaurantes/com-frete-gratis")
	public List<Restaurante> restaurantesComFreteGratis(String nmRestaurante) {
		return restauranteRepository.findComFreteGratis(nmRestaurante);
	}
	
	@GetMapping("/restaurantes/primeiro")
	public Optional<Restaurante> restaurantePrimeiro() {
		return restauranteRepository.buscarPrimeiro();
	}
	
	@GetMapping("/cozinhas/primeira")
	public Optional<Cozinha> cozinhaPrimeira() {
		return cozinhaRepository.buscarPrimeiro();
	}
	
}