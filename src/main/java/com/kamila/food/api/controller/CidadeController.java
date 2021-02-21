package com.kamila.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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

import com.kamila.food.domain.exception.EstadoNaoEncontradoException;
import com.kamila.food.domain.exception.NegocioException;
import com.kamila.food.domain.model.Cidade;
import com.kamila.food.domain.repository.CidadeRepository;
import com.kamila.food.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@GetMapping
	public List<Cidade> listar() {
		return cidadeRepository.findAll();
	}
	
	@GetMapping("/{idCidade}")
	public Cidade buscar(@PathVariable Long idCidade) {
		return cadastroCidadeService.buscarOuFalhar(idCidade);

	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade salvar(@RequestBody @Valid Cidade cidade) {
		try {
			return cadastroCidadeService.salvar(cidade);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e); // Alterando código de erro para 400
		}
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Cidade atualizar(@PathVariable Long id, @RequestBody @Valid Cidade cidade) {
		Cidade cidadeAtual = cadastroCidadeService.buscarOuFalhar(id);

		BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		
		try {
			return cadastroCidadeService.salvar(cidadeAtual);
		} catch (EstadoNaoEncontradoException e) {
			// Caso não existisse tratarNegocioException(), ao inserir a causa oriunda de EstadoNaoEncontradoException
			// essa exceção seria capturada pelo tratarEntidadeNaoEncontradaException().
			// Se a causa não for inserida no constructor não entrará no método tratarEntidadeNaoEncontradaException()
			throw new NegocioException(e.getMessage());
		}
	}

	@DeleteMapping("/{idCidade}")
	public void remover(@PathVariable Long idCidade) {
		cadastroCidadeService.remover(idCidade);
	}
	
}
