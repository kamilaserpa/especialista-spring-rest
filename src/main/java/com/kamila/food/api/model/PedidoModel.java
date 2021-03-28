package com.kamila.food.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoModel {

	private String codigo;
	
	private BigDecimal subtotal;

	private BigDecimal taxaFrete;

	private BigDecimal valorTotal;

	private OffsetDateTime dataCriacao;

	private OffsetDateTime dataConfirmacao;

	private OffsetDateTime dataCancelamento;

	private OffsetDateTime dataEntrega;

	private String status;
	
	private FormaPagamentoModel formaPagamento;

	private RestauranteResumoModel restaurante;

	private EnderecoModel enderecoEntrega;

	private UsuarioModel cliente;

	private List<ItemPedidoModel> itens;

}
