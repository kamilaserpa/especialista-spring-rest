package com.kamila.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kamila.food.domain.exception.CidadeNaoEncontradaException;
import com.kamila.food.domain.exception.EntidadeEmUsoException;
import com.kamila.food.domain.model.Cidade;
import com.kamila.food.domain.model.Estado;
import com.kamila.food.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {

	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso.";

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstadoService;

	@Transactional
	public Cidade salvar(Cidade cidade) {
		Long idEstado = cidade.getEstado().getId();

		Estado estado = cadastroEstadoService.buscarOuFalhar(idEstado);

		cidade.setEstado(estado);

		return cidadeRepository.save(cidade);
	}
	
	@Transactional
	public void remover(Long idCidade) {
		try {
			cidadeRepository.deleteById(idCidade);
			cidadeRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(idCidade);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_CIDADE_EM_USO, idCidade));
		}
	}

	public Cidade buscarOuFalhar(Long idCidade) {
		return cidadeRepository.findById(idCidade).orElseThrow(
				() -> new CidadeNaoEncontradaException(idCidade));
	}

}
