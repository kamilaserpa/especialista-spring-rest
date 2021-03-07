package com.kamila.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kamila.food.domain.exception.CozinhaNaoEncontradaException;
import com.kamila.food.domain.exception.EntidadeEmUsoException;
import com.kamila.food.domain.model.Cozinha;
import com.kamila.food.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	private static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso.";

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Transactional
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}

	@Transactional
	public void remover(Long idCozinha) {
		try {
			cozinhaRepository.deleteById(idCozinha);
			
		} catch (EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncontradaException(idCozinha);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_COZINHA_EM_USO, idCozinha));
		}
	}

	public Cozinha buscarOuFalhar(Long idCozinha) {
		return cozinhaRepository.findById(idCozinha).orElseThrow(() -> new CozinhaNaoEncontradaException(idCozinha));
	}

}
