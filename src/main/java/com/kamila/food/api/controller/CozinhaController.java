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

import com.kamila.food.api.assembler.CozinhaInputDisassembler;
import com.kamila.food.api.assembler.CozinhaModelAssembler;
import com.kamila.food.api.model.CozinhaModel;
import com.kamila.food.api.model.input.CozinhaInput;
import com.kamila.food.domain.model.Cozinha;
import com.kamila.food.domain.repository.CozinhaRepository;
import com.kamila.food.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;

	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;  
	
	@GetMapping
	public List<CozinhaModel> listar() {
		List<Cozinha> todasCozinhas = cozinhaRepository.findAll();
	    
	    return cozinhaModelAssembler.toCollectionModel(todasCozinhas);
	}

	@GetMapping("/{idCozinha}")
	public CozinhaModel buscar(@PathVariable Long idCozinha) {
		Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(idCozinha);

		return cozinhaModelAssembler.toModel(cozinha);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel salvar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
		
	    cozinha = cadastroCozinhaService.salvar(cozinha);
	    
	    return cozinhaModelAssembler.toModel(cozinha);
	}

	@PutMapping("/{idCozinha}")
	@ResponseStatus(HttpStatus.OK)
	public CozinhaModel atualizar(@PathVariable Long idCozinha, @RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(idCozinha);

		cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);

		cozinhaAtual = cadastroCozinhaService.salvar(cozinhaAtual);
	    
	    return cozinhaModelAssembler.toModel(cozinhaAtual);
	}
	
	@DeleteMapping("/{idCozinha}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long idCozinha) {
		cadastroCozinhaService.remover(idCozinha);
	}

}
