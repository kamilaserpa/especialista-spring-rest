package com.kamila.food.api.v1.controller;

import com.kamila.food.api.v1.assembler.CozinhaInputDisassembler;
import com.kamila.food.api.v1.assembler.CozinhaModelAssembler;
import com.kamila.food.api.v1.model.CozinhaModel;
import com.kamila.food.api.v1.model.input.CozinhaInput;
import com.kamila.food.api.v1.openapi.controller.CozinhaControllerOpenApi;
import com.kamila.food.core.security.CheckSecurity;
import com.kamila.food.domain.model.Cozinha;
import com.kamila.food.domain.repository.CozinhaRepository;
import com.kamila.food.domain.service.CadastroCozinhaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/v1/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;

	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;

	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

	@CheckSecurity.Cozinhas.PodeConsultar // Antes de acessar o método realiza a pre autorização
	@Override
	@GetMapping
	public PagedModel<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable) { // Alterando padrão size que por default é 20
		log.info("Consultando cozinhas com páginas de {} registros", pageable.getPageSize());

		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

		// Convertendo Page em PagedModel. Já 'cozinhaModelAssembler' converte Cozinha em CozinhaModel
		PagedModel<CozinhaModel> cozinhasPagedModel = pagedResourcesAssembler
				.toModel(cozinhasPage, cozinhaModelAssembler);

		return cozinhasPagedModel;
	}

	@CheckSecurity.Cozinhas.PodeConsultar // Antes de acessar o método realiza a pre autorização
	@Override
	@GetMapping("/{idCozinha}")
	public CozinhaModel buscar(@PathVariable Long idCozinha) {
		Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(idCozinha);

		return cozinhaModelAssembler.toModel(cozinha);
	}

	@CheckSecurity.Cozinhas.PodeEditar
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel salvar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
		
	    cozinha = cadastroCozinhaService.salvar(cozinha);
	    
	    return cozinhaModelAssembler.toModel(cozinha);
	}

	@CheckSecurity.Cozinhas.PodeEditar
	@Override
	@PutMapping("/{idCozinha}")
	@ResponseStatus(HttpStatus.OK)
	public CozinhaModel atualizar(@PathVariable Long idCozinha, @RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(idCozinha);

		cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);

		cozinhaAtual = cadastroCozinhaService.salvar(cozinhaAtual);
	    
	    return cozinhaModelAssembler.toModel(cozinhaAtual);
	}

	@CheckSecurity.Cozinhas.PodeEditar
	@Override
	@DeleteMapping("/{idCozinha}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long idCozinha) {
		cadastroCozinhaService.remover(idCozinha);
	}

}
