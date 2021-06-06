package com.kamila.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kamila.food.api.assembler.ProdutoInputDisassembler;
import com.kamila.food.api.assembler.ProdutoModelAssembler;
import com.kamila.food.api.model.ProdutoModel;
import com.kamila.food.api.model.input.ProdutoInput;
import com.kamila.food.domain.model.Produto;
import com.kamila.food.domain.model.Restaurante;
import com.kamila.food.domain.repository.ProdutoRepository;
import com.kamila.food.domain.service.CadastroProdutoService;
import com.kamila.food.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{idRestaurante}/produtos")
public class RestauranteProdutoController {

	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;

	@Autowired
	private CadastroProdutoService cadastroProdutoService;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ProdutoModelAssembler produtoModelAssembler;

	@Autowired
	private ProdutoInputDisassembler produtoInputDisassembler;

	@GetMapping
	public List<ProdutoModel> listar(@PathVariable Long idRestaurante,
			@RequestParam(required = false) boolean incluirInativos) {
		
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(idRestaurante);
		List<Produto> todosProdutos = null;
		
		if (incluirInativos) {
			todosProdutos = produtoRepository.findByRestaurante(restaurante);
		} else {
			todosProdutos = produtoRepository.findAtivosByRestaurante(restaurante);
		}
		return produtoModelAssembler.toCollectionModel(todosProdutos);
	}

	@GetMapping("/{idProduto}")
	public ProdutoModel buscar(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {
		Produto produto = cadastroProdutoService.buscarOuFalhar(idRestaurante, idProduto);
		return produtoModelAssembler.toModel(produto);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoModel adicionar(@PathVariable Long idRestaurante, @RequestBody @Valid ProdutoInput produtoInput) {
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(idRestaurante);

		Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
		produto.setRestaurante(restaurante);

		produto = cadastroProdutoService.salvar(produto);

		return produtoModelAssembler.toModel(produto);
	}

	@PutMapping("/{idProduto}")
	public ProdutoModel atualizar(@PathVariable Long idRestaurante, @PathVariable Long idProduto,
			@RequestBody @Valid ProdutoInput produtoInput) {
		
		Produto produtoAtual = cadastroProdutoService.buscarOuFalhar(idRestaurante, idProduto);
		produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);

		produtoAtual = cadastroProdutoService.salvar(produtoAtual);

		return produtoModelAssembler.toModel(produtoAtual);
	}

}
