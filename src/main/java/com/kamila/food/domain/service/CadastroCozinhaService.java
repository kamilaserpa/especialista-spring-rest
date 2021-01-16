package com.kamila.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.kamila.food.domain.exception.EntidadeEmUsoException;
import com.kamila.food.domain.exception.EntidadeNaoEncontradaException;
import com.kamila.food.domain.model.Cozinha;
import com.kamila.food.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.salvar(cozinha);
	}

	public void remover(Long idCozinha) {
		try {
			cozinhaRepository.remover(idCozinha);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de cozinha com código %d ", idCozinha));
		} catch (DataIntegrityViolationException e) {
			// Caso entidade não possa ser deletada por ter outros objetos relacionados (foreign Key)
			throw new EntidadeEmUsoException(
					String.format("Cozinha de código %d não pode ser removida, pois está em uso", idCozinha));
		}
	}

}
