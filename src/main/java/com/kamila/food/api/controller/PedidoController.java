package com.kamila.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.kamila.food.api.assembler.PedidoInputDisassembler;
import com.kamila.food.api.assembler.PedidoModelAssembler;
import com.kamila.food.api.assembler.PedidoResumoModelAssembler;
import com.kamila.food.api.model.PedidoModel;
import com.kamila.food.api.model.PedidoResumoModel;
import com.kamila.food.api.model.input.PedidoInput;
import com.kamila.food.domain.exception.EntidadeNaoEncontradaException;
import com.kamila.food.domain.exception.NegocioException;
import com.kamila.food.domain.model.Pedido;
import com.kamila.food.domain.model.Usuario;
import com.kamila.food.domain.service.CadastroProdutoService;
import com.kamila.food.domain.service.EmissaoPedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	EmissaoPedidoService emissaoPedidoService;
	
	@Autowired
	CadastroProdutoService cadastroProdutoService;

	@Autowired
	PedidoModelAssembler pedidoModelAssembler;
	
	@Autowired
	PedidoResumoModelAssembler pedidoResumoModelAssembler;

	@Autowired
	PedidoInputDisassembler pedidoInputDisassembler;
	
	@GetMapping
	public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
		List<Pedido> pedidos = emissaoPedidoService.findAll();
		List<PedidoResumoModel> pedidosModel = pedidoResumoModelAssembler.toCollectionModel(pedidos);
		// Instancia envelope de pedidos
		MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);
		
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		// Retorna apenas as propriedades mencionadas, filtra as demais
		filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
		
		if (StringUtils.isNotBlank(campos)) {
			filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
		}
		
		pedidosWrapper.setFilters(filterProvider);
		return pedidosWrapper;
	}
	
	@GetMapping("/resumo")
	public List<PedidoResumoModel> listarPedidosResumo() {
		return pedidoResumoModelAssembler.toCollectionModel(emissaoPedidoService.findAll());
	}

	@GetMapping("/{codigoPedido}")
	public PedidoModel buscar(@PathVariable String codigoPedido) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);

		return pedidoModelAssembler.toModel(pedido);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
	    try {
	        Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

	        // TODO pegar usuário autenticado
	        novoPedido.setCliente(new Usuario());
	        novoPedido.getCliente().setId(1L);

	        novoPedido = emissaoPedidoService.emitir(novoPedido);

	        return pedidoModelAssembler.toModel(novoPedido);
	    } catch (EntidadeNaoEncontradaException e) {
	        throw new NegocioException(e.getMessage(), e);
	    }
	}
	
}
