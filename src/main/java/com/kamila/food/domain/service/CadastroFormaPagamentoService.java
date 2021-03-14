package com.kamila.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kamila.food.domain.exception.EntidadeEmUsoException;
import com.kamila.food.domain.exception.FormaPagamentoNaoEncontradaException;
import com.kamila.food.domain.model.FormaPagamento;
import com.kamila.food.domain.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaPagamentoService {

	private static final String MSG_FORMA_PAGAMENTO_EM_USO = "Forma de Pagamento de código %d não pode ser removida, pois está em uso.";
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;

	@Transactional
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return formaPagamentoRepository.save(formaPagamento);
	}

	@Transactional
	public void remover(Long idFormaPagamento) {
		try {
			formaPagamentoRepository.deleteById(idFormaPagamento);
			formaPagamentoRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new FormaPagamentoNaoEncontradaException(idFormaPagamento);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_FORMA_PAGAMENTO_EM_USO, idFormaPagamento));
		}
	}

	public FormaPagamento buscarOuFalhar(Long idFormaPagamento) {
		return formaPagamentoRepository.findById(idFormaPagamento).orElseThrow(
				() -> new FormaPagamentoNaoEncontradaException(idFormaPagamento));
	}

}
