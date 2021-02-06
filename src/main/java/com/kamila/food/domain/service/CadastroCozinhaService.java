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

	private static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso.";

	private static final String MSG_COZINHA_NAO_ENCONTRADA = "Não existe cadastro de cozinha com código %d.";
	
	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}

	public void remover(Long idCozinha) {
		try {
			cozinhaRepository.deleteById(idCozinha);
			
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format(MSG_COZINHA_NAO_ENCONTRADA, idCozinha));
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_COZINHA_EM_USO, idCozinha));
		}
	}

	public Cozinha buscarOuFalhar(Long idCozinha) {
		return cozinhaRepository.findById(idCozinha).orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format(MSG_COZINHA_NAO_ENCONTRADA, idCozinha)));
	}

}
