package com.kamila.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.kamila.food.domain.exception.EntidadeEmUsoException;
import com.kamila.food.domain.exception.EntidadeNaoEncontradaException;
import com.kamila.food.domain.model.Cidade;
import com.kamila.food.domain.model.Estado;
import com.kamila.food.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {

	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso.";

	private static final String MSG_CIDADE_NAO_ENCONTRADA = "Não existe cadastro de cidade com código %d .";
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstadoSerevice;

	public Cidade salvar(Cidade cidade) {
		Long idEstado = cidade.getEstado().getId();

		Estado estado = cadastroEstadoSerevice.buscarOuFalhar(idEstado);

		cidade.setEstado(estado);

		return cidadeRepository.save(cidade);
	}

	public void remover(Long idCidade) {
		try {
			cidadeRepository.deleteById(idCidade);

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(MSG_CIDADE_NAO_ENCONTRADA, idCidade));

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_CIDADE_EM_USO, idCidade));
		}
	}

	public Cidade buscarOuFalhar(Long idCidade) {
		return cidadeRepository.findById(idCidade).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(MSG_CIDADE_NAO_ENCONTRADA, idCidade)));
	}

}
