package com.kamila.food.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kamila.food.domain.exception.PedidoNaoEncontradoException;
import com.kamila.food.domain.model.Pedido;
import com.kamila.food.domain.repository.PedidoRepository;

@Service
public class EmissaoPedidoService {

	@Autowired
	PedidoRepository pedidoRepository;

	public List<Pedido> findAll() {
		return pedidoRepository.findAll();
	}

	public Pedido buscarOuFalhar(Long idPedido) {
		return pedidoRepository.findById(idPedido)
				.orElseThrow(() -> new PedidoNaoEncontradoException(idPedido));
	}

}
