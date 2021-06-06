package com.kamila.food.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kamila.food.api.assembler.FormaPagamentoModelAssembler;
import com.kamila.food.api.model.FormaPagamentoModel;
import com.kamila.food.domain.model.Restaurante;
import com.kamila.food.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{idRestaurante}/formas-pagamento")
public class RestauranteFormaPagamentoController {

	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
	
	@GetMapping
	public List<FormaPagamentoModel> listar(@PathVariable Long idRestaurante) {
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(idRestaurante);
		return formaPagamentoModelAssembler.toCollectionModel(restaurante.getFormasPagamento());
	}

	@DeleteMapping("/{idFormaPagamento}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long idRestaurante, @PathVariable Long idFormaPagamento) {
		cadastroRestauranteService.desassociarFormaPagamento(idRestaurante, idFormaPagamento);
	}
	
	@PutMapping("/{idFormaPagamento}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long idRestaurante, @PathVariable Long idFormaPagamento) {
		cadastroRestauranteService.associarFormaPagamento(idRestaurante, idFormaPagamento);
	}
	
	
}
