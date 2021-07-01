package com.kamila.food.api.controller;

import com.kamila.food.api.assembler.EstadoInputDisassembler;
import com.kamila.food.api.assembler.EstadoModelAssembler;
import com.kamila.food.api.model.EstadoModel;
import com.kamila.food.api.model.input.EstadoInput;
import com.kamila.food.api.openapi.controller.EstadoControllerOpenApi;
import com.kamila.food.domain.model.Estado;
import com.kamila.food.domain.repository.EstadoRepository;
import com.kamila.food.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {

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
