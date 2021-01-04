package com.kamila.food.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_cozinha")
public class Cozinha {

	@Id
	@Column(name = "id_cozinha")
	private Long idCozinha;

	@Column(name = "nm_cozinha", length = 30)
	private String nmCozinha;

	public Long getIdCozinha() {
		return idCozinha;
	}

	public void setIdCozinha(Long idCozinha) {
		this.idCozinha = idCozinha;
	}

	public String getNmCozinha() {
		return nmCozinha;
	}

	public void setNmCozinha(String nmCozinha) {
		this.nmCozinha = nmCozinha;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCozinha == null) ? 0 : idCozinha.hashCode());
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
		Cozinha other = (Cozinha) obj;
		if (idCozinha == null) {
			if (other.idCozinha != null)
				return false;
		} else if (!idCozinha.equals(other.idCozinha))
			return false;
		return true;
	}

}
