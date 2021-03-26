package com.kamila.food.domain.service;


import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kamila.food.domain.exception.NegocioException;
import com.kamila.food.domain.model.Pedido;
import com.kamila.food.domain.model.StatusPedido;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedidoService;
	
	@Transactional
	public void confirmar(Long idPedido) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(idPedido);
		
		if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
			throw new NegocioException(
					String.format("Status do pedido %d não não pode ser alterado de %s para %s", 
							pedido.getId(), pedido.getStatus().getDecricao(),
							StatusPedido.CONFIRMADO.getDecricao()));
		}
		
		pedido.setStatus(StatusPedido.CONFIRMADO);
		pedido.setDataConfirmacao(OffsetDateTime.now());
	}
	
	@Transactional
	public void entregar(Long idPedido) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(idPedido);
		
		if (!pedido.getStatus().equals(StatusPedido.CONFIRMADO)) {
			throw new NegocioException(
					String.format("Status do pedido %d não não pode ser alterado de %s para %s", 
							pedido.getId(), pedido.getStatus().getDecricao(),
							StatusPedido.ENTREGUE.getDecricao()));
		}
		
		pedido.setStatus(StatusPedido.ENTREGUE);
		pedido.setDataConfirmacao(OffsetDateTime.now());
	}
	
	@Transactional
	public void cancelar(Long idPedido) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(idPedido);
		
		if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
			throw new NegocioException(
					String.format("Status do pedido %d não não pode ser alterado de %s para %s", 
							pedido.getId(), pedido.getStatus().getDecricao(),
							StatusPedido.CANCELADO.getDecricao()));
		}
		
		pedido.setStatus(StatusPedido.CANCELADO);
		pedido.setDataConfirmacao(OffsetDateTime.now());
	}
	
}
