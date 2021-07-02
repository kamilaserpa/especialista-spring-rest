package com.kamila.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.kamila.food.api.openapi.controller.CozinhaControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping(value = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;

	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;

	@Override
	@GetMapping
	public Page<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable) { // Alterando padrão size que por default é 20
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

		List<CozinhaModel> cozinhasModel = cozinhaModelAssembler.toCollectionModel(cozinhasPage.getContent());

		// Convertendo conteúdo do Page para classe de modelo
		Page<CozinhaModel> cozinhasModelPage = new PageImpl<>(cozinhasModel, pageable, cozinhasPage.getTotalElements());
		
		return cozinhasModelPage;
	}

	@Override
	@GetMapping("/{idCozinha}")
	public CozinhaModel buscar(@PathVariable Long idCozinha) {
		Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(idCozinha);

		return cozinhaModelAssembler.toModel(cozinha);
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel salvar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
		
	    cozinha = cadastroCozinhaService.salvar(cozinha);
	    
	    return cozinhaModelAssembler.toModel(cozinha);
	}

	@Override
	@PutMapping("/{idCozinha}")
	@ResponseStatus(HttpStatus.OK)
	public CozinhaModel atualizar(@PathVariable Long idCozinha, @RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(idCozinha);

		cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);

		cozinhaAtual = cadastroCozinhaService.salvar(cozinhaAtual);
	    
	    return cozinhaModelAssembler.toModel(cozinhaAtual);
	}

	@Override
	@DeleteMapping("/{idCozinha}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long idCozinha) {
		cadastroCozinhaService.remover(idCozinha);
	}

}
