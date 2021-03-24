package com.kamila.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kamila.food.domain.exception.EntidadeEmUsoException;
import com.kamila.food.domain.exception.RestauranteNaoEncontradoException;
import com.kamila.food.domain.model.Cidade;
import com.kamila.food.domain.model.Cozinha;
import com.kamila.food.domain.model.FormaPagamento;
import com.kamila.food.domain.model.Restaurante;
import com.kamila.food.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser removido, pois está em uso.";

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Autowired
	CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	CadastroFormaPagamentoService cadastroFormaPagamentoService;

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long idCozinha = restaurante.getCozinha().getId();
		Long idCidade = restaurante.getEndereco().getCidade().getId();
		
		// Validando se existem cozinha e cidade com o id informado
		Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(idCozinha);
		Cidade cidade = cadastroCidadeService.buscarOuFalhar(idCidade);
		
		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);

		return restauranteRepository.save(restaurante);
	}

	@Transactional
	public void remover(Long idRestaurante) {
		try {
			restauranteRepository.deleteById(idRestaurante);
			restauranteRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradoException(idRestaurante);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_RESTAURANTE_EM_USO, idRestaurante));
		}
	}
	
	@Transactional
	public void ativar(Long idRestaurante) {
		Restaurante restauranteAtual = buscarOuFalhar(idRestaurante);
		// Dada a captura de uma instância em estado gerenciado pelo contexto de persistência do JPA
		// qualquer alteração nesse objeto será sincronizada com o banco de dados ao final da transação 
		restauranteAtual.ativar();
	}
	
	@Transactional
	public void inativar(Long idRestaurante) {
		Restaurante restauranteAtual = buscarOuFalhar(idRestaurante);
		restauranteAtual.inativar();
	}

	public Restaurante buscarOuFalhar(Long idRestaurante) {
		return restauranteRepository.findById(idRestaurante)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(idRestaurante));
	}
	
	@Transactional
	public void desassociarFormaPagamento(Long idRestaurante, Long idFormaPagamento) {
		Restaurante restaurante = buscarOuFalhar(idRestaurante);
		
		// Valida se formaPagamento existe
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(idFormaPagamento);
		
		restaurante.removerFormaPagamento(formaPagamento);
		// Desnecessário chamar .save() do restaurante por causa do @Transactional que irá realizar a sincronização com o banco
	}
	
	@Transactional
	public void associarFormaPagamento(Long idRestaurante, Long idFormaPagamento) {
		Restaurante restaurante = buscarOuFalhar(idRestaurante);
		
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(idFormaPagamento);
		
		restaurante.adicionarFormaPagamento(formaPagamento);
	}

	@Transactional
	public void abrir(Long restauranteId) {
	    Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
	    
	    restauranteAtual.abrir();
	}

	@Transactional
	public void fechar(Long restauranteId) {
	    Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
	    
	    restauranteAtual.fechar();
	}
	
}
