package com.kamila.food.domain.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kamila.food.domain.model.Pedido;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedidoService;
	
	@Transactional
	public void confirmar(Long idPedido) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(idPedido);
		pedido.confirmar();
	}
	
	@Transactional
	public void entregar(Long idPedido) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(idPedido);
		pedido.entregar();
	}
	
	@Transactional
	public void cancelar(Long idPedido) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(idPedido);
		pedido.cancelar();
	}
	
}
