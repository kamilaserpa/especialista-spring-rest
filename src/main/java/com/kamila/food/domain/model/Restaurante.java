package com.kamila.food.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.kamila.food.core.validation.ValorZeroIncluiDescricao;

import lombok.Data;
import lombok.EqualsAndHashCode;

@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_restaurante")
public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_restaurante")
	private Long id;

	@Column(name = "nm_restaurante", length = 100, nullable = false)
	private String nome;

	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;
	
	@ManyToOne // (fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cozinha", nullable = false)
	private Cozinha cozinha;

	@Embedded // Incorporação
	private Endereco endereco;
	
	private Boolean ativo = Boolean.TRUE;

	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime") // datetime para remover precisão de milisegundos q seria CreationTimestamp(6)
	private OffsetDateTime dataCadastro;

	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;

	@ManyToMany
	@JoinTable(name = "tb_restaurante_forma_pagamento", 
		joinColumns = @JoinColumn(name = "id_restaurante"), // Indica a coluna na tabela local (Restaurante) que será o nome da coluna relacionada
		inverseJoinColumns = @JoinColumn(name = "id_forma_pagamento"))
	private Set<FormaPagamento> formasPagamento = new HashSet<>(); 
	// Set é um conjunto que não aceita elementos duplicados, evitando erro de restaurante receber forma de pagamento já associada

	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();

	private Boolean aberto = Boolean.FALSE;

	public void abrir() {
	    setAberto(true);
	}

	public void fechar() {
	    setAberto(false);
	} 
	
	// Tornando mais claro o código
	public void ativar() {
		setAtivo(true);
	}
	
	public void inativar() {
		setAtivo(false);
	}
	
	public boolean removerFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().remove(formaPagamento);
	}

	public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().add(formaPagamento);
	}
	
}
