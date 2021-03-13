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

import com.kamila.food.api.assembler.EstadoInputDisassembler;
import com.kamila.food.api.assembler.EstadoModelAssembler;
import com.kamila.food.api.model.EstadoModel;
import com.kamila.food.api.model.input.EstadoInput;
import com.kamila.food.domain.model.Estado;
import com.kamila.food.domain.repository.EstadoRepository;
import com.kamila.food.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;
	
	@Autowired
	private EstadoModelAssembler estadoModelAssembler;
	
	@GetMapping
	public List<EstadoModel> listar() {
		return estadoModelAssembler.toCollectionModel(estadoRepository.findAll());
	}
	
	@GetMapping("/{idEstado}")
	public EstadoModel buscar(@PathVariable Long idEstado) {
		return estadoModelAssembler.toModel(cadastroEstadoService.buscarOuFalhar(idEstado));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoModel salvar(@RequestBody @Valid EstadoInput estadoInput) {
		Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);
		
		estado = cadastroEstadoService.salvar(estado);
		
		return estadoModelAssembler.toModel(estado);
	}

	@PutMapping("/{idEstado}")
	@ResponseStatus(HttpStatus.OK)
	public EstadoModel atualizar(@PathVariable Long idEstado,
			@RequestBody @Valid EstadoInput estadoInput) {
		
		Estado estadoAtual = cadastroEstadoService.buscarOuFalhar(idEstado);
		
		estadoInputDisassembler.copoyToDomainObject(estadoInput, estadoAtual);
		
		estadoAtual = cadastroEstadoService.salvar(estadoAtual);
				
		return estadoModelAssembler.toModel(estadoAtual);
	}

	@DeleteMapping("/{idEstado}")
	public void remover(@PathVariable Long idEstado) {
			cadastroEstadoService.remover(idEstado);
	}
	
}
