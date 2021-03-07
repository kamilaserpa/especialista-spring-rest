package com.kamila.food.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kamila.food.core.validation.Groups;
import com.kamila.food.core.validation.Multiplo;
import com.kamila.food.core.validation.TaxaFrete;
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

	@NotBlank // Verifica se nullo, vazio ou contém apenas espaços
	@Column(name = "nm_restaurante", length = 100, nullable = false)
	private String nome;

	@NotNull
	@TaxaFrete
	@Multiplo(numero = 5)
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;
	
	@JsonIgnoreProperties(value = "nome", allowGetters = true) // Ao desserializar (json to java object) um Restaurante ignora o nome de Cozinha
	@Valid
	@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class) // Qdo validar a cozinha converta o group Default.class para CozinhaId.class
	@NotNull
	@ManyToOne // (fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cozinha", nullable = false)
	private Cozinha cozinha;

	@JsonIgnore
	@Embedded // Incorporação
	private Endereco endereco;

	@JsonIgnore
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime") // datetime para remover precisão de milisegundos q seria CreationTimestamp(6)
	private LocalDateTime dataCadastro;

	@JsonIgnore
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataAtualizacao;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "tb_restaurante_forma_pagamento", 
		joinColumns = @JoinColumn(name = "id_restaurante"), // Indica a coluna na tabela local (Restaurante) que será o nome da coluna relacionada
		inverseJoinColumns = @JoinColumn(name = "id_forma_pagamento"))
	private List<FormaPagamento> formasPagamento = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();

}
