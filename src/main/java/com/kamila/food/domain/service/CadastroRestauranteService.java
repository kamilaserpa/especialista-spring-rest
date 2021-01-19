package com.kamila.food.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.kamila.food.domain.exception.EntidadeEmUsoException;
import com.kamila.food.domain.exception.EntidadeNaoEncontradaException;
import com.kamila.food.domain.model.Cozinha;
import com.kamila.food.domain.model.Restaurante;
import com.kamila.food.domain.repository.CozinhaRepository;
import com.kamila.food.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Restaurante salvar(Restaurante restaurante) {
		Long idCozinha = restaurante.getCozinha().getId();
		Optional<Cozinha> cozinha = cozinhaRepository.findById(idCozinha);

		if (cozinha == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de cozinha com código %d .", idCozinha));
		}

		restaurante.setCozinha(cozinha.get());

		return restauranteRepository.salvar(restaurante);
	}

	public void remover(Long idRestaurante) {
		try {
			restauranteRepository.remover(idRestaurante);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de restaurante com código %d .", idRestaurante));
		} catch (DataIntegrityViolationException e) {
			// Caso entidade não possa ser deletada por ter outros objetos relacionados (foreign Key)
			throw new EntidadeEmUsoException(
					String.format("Restaurante de código %d não pode ser removido, pois está em uso.", idRestaurante));
		}
	}

}
