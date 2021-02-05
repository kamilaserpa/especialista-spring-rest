package com.kamila.food.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name="tb_item_pedido")
public class ItemPedido {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_item_pedido")
	private Long id;
	
	private BigDecimal precoUnitario;
	
	private BigDecimal precoTotal;
	
	private Integer quantidade;
	
	private String observacao;
	
	@ManyToOne
	@JoinColumn(name="id_produto", nullable = false)
	private Produto produto;
	
	@ManyToOne
	@JoinColumn(name="id_pedido", nullable = false)
	private Pedido pedido;
	
}
