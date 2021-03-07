package com.kamila.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kamila.food.domain.exception.EntidadeEmUsoException;
import com.kamila.food.domain.exception.EstadoNaoEncontradoException;
import com.kamila.food.domain.model.Estado;
import com.kamila.food.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	private static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removido, pois está em uso.";
	
	@Autowired
	private EstadoRepository estadoRepository;

	@Transactional
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}

	@Transactional
	public void remover(Long idEstado) {
		try {
			estadoRepository.deleteById(idEstado);
			
		} catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradoException(idEstado);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ESTADO_EM_USO, idEstado));
		}
	}

	public Estado buscarOuFalhar(Long idEstado) {
		return estadoRepository.findById(idEstado).orElseThrow(
				() -> new EstadoNaoEncontradoException(idEstado));
	}

}
