package com.kamila.food.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kamila.food.api.assembler.PedidoModelAssembler;
import com.kamila.food.api.assembler.PedidoResumoModelAssembler;
import com.kamila.food.api.model.PedidoModel;
import com.kamila.food.api.model.PedidoResumoModel;
import com.kamila.food.domain.model.Pedido;
import com.kamila.food.domain.service.EmissaoPedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	EmissaoPedidoService emissaoPedidoService;

	@Autowired
	PedidoModelAssembler pedidoModelAssembler;
	
	@Autowired
	PedidoResumoModelAssembler pedidoResumoModelAssembler;

	@GetMapping
	public List<PedidoResumoModel> listar() {
		return pedidoResumoModelAssembler.toCollectionModel(emissaoPedidoService.findAll());
	}

	@GetMapping("/{idPedido}")
	public PedidoModel buscar(@PathVariable Long idPedido) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(idPedido);

		return pedidoModelAssembler.toModel(pedido);
	}
}
