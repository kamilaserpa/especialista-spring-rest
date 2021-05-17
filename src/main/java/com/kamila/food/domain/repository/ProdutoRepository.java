package com.kamila.food.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kamila.food.domain.model.Produto;
import com.kamila.food.domain.model.Restaurante;

public interface ProdutoRepository
		extends JpaRepository<Produto, Long>, ProdutoRepositoryQueries {

	@Query("from Produto where restaurante.id = :idRestaurante and id = :idProduto")
	Optional<Produto> findById(@Param("idRestaurante") Long idRestaurante, @Param("idProduto") Long idProduto);
	
	List<Produto> findByRestaurante(Restaurante restaurante);
	
	@Query("from Produto p where p.ativo = true and p.restaurante = :restaurante")
	List<Produto> findAtivosByRestaurante(Restaurante restaurante);
	
}
