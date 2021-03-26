package com.kamila.food.domain.repository;

import org.springframework.stereotype.Repository;

import com.kamila.food.domain.model.Pedido;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {

}
