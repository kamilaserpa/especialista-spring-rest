package com.kamila.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kamila.food.domain.exception.EntidadeEmUsoException;
import com.kamila.food.domain.exception.RestauranteNaoEncontradoException;
import com.kamila.food.domain.model.Cozinha;
import com.kamila.food.domain.model.Restaurante;
import com.kamila.food.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser removido, pois está em uso.";

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long idCozinha = restaurante.getCozinha().getId();
		
		Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(idCozinha);
		
		restaurante.setCozinha(cozinha);

		return restauranteRepository.save(restaurante);
	}

	@Transactional
	public void remover(Long idRestaurante) {
		try {
			restauranteRepository.deleteById(idRestaurante);
			
		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradoException(idRestaurante);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_RESTAURANTE_EM_USO, idRestaurante));
		}
	}

	public Restaurante buscarOuFalhar(Long idRestaurante) {
		return restauranteRepository.findById(idRestaurante)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(idRestaurante));
	}

}
