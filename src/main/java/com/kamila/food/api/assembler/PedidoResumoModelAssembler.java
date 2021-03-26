package com.kamila.food.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kamila.food.api.model.PedidoResumoModel;
import com.kamila.food.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public PedidoResumoModel toModel(Pedido pedido) {
		return modelMapper.map(pedido, PedidoResumoModel.class);
	}

	public List<PedidoResumoModel> toCollectionModel(Collection<Pedido> pedidos) {
		return pedidos.stream()
				.map(pedido -> toModel(pedido))
				.collect(Collectors.toList());
	}

}
