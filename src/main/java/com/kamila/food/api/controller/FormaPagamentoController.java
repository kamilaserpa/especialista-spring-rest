package com.kamila.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kamila.food.api.assembler.FormaPagamentoInputDisassembler;
import com.kamila.food.api.assembler.FormaPagamentoModelAssembler;
import com.kamila.food.api.model.FormaPagamentoModel;
import com.kamila.food.api.model.input.FormaPagamentoInput;
import com.kamila.food.domain.exception.EstadoNaoEncontradoException;
import com.kamila.food.domain.exception.NegocioException;
import com.kamila.food.domain.model.FormaPagamento;
import com.kamila.food.domain.repository.FormaPagamentoRepository;
import com.kamila.food.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
	
	@Autowired
	private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;
	
	
	@GetMapping
	public List<FormaPagamento> listar() {
		return formaPagamentoRepository.findAll();
	}
	
	@GetMapping("/{idFormaPagamento}")
	public FormaPagamentoModel buscar(@PathVariable Long idFormaPagamento) {
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(idFormaPagamento);
		return formaPagamentoModelAssembler.toModel(formaPagamento);

	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoModel salvar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		try {
			FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);
			return formaPagamentoModelAssembler.toModel(cadastroFormaPagamentoService.salvar(formaPagamento));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public FormaPagamentoModel atualizar(@PathVariable Long id, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		try {
			FormaPagamento formaPagamentoAtual = cadastroFormaPagamentoService.buscarOuFalhar(id);

			formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);

			formaPagamentoAtual = cadastroFormaPagamentoService.salvar(formaPagamentoAtual);
			
			return formaPagamentoModelAssembler.toModel(formaPagamentoAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@DeleteMapping("/{idFormaPagamento}")
	public void remover(@PathVariable Long idFormaPagamento) {
		cadastroFormaPagamentoService.remover(idFormaPagamento);
	}
	
}
