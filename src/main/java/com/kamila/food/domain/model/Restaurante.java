package com.kamila.food.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Restaurante {

	@Id
	private Long idRestaurante;

	@Column(name = "nm_restaurante")
	private String nmRestaurante;

	@Column(name = "taxa_frete")
	private BigDecimal taxaFrete;

	public Long getIdRestaurante() {
		return idRestaurante;
	}

	public void setIdRestaurante(Long idRestaurante) {
		this.idRestaurante = idRestaurante;
	}

	public String getNome() {
		return nmRestaurante;
	}

	public void setNome(String nome) {
		this.nmRestaurante = nome;
	}

	public BigDecimal getTaxaFrete() {
		return taxaFrete;
	}

	public void setTaxaFrete(BigDecimal taxaFrete) {
		this.taxaFrete = taxaFrete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idRestaurante == null) ? 0 : idRestaurante.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Restaurante other = (Restaurante) obj;
		if (idRestaurante == null) {
			if (other.idRestaurante != null)
				return false;
		} else if (!idRestaurante.equals(other.idRestaurante))
			return false;
		return true;
	}

}
