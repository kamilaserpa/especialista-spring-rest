package com.kamila.food.domain.repository;

import java.util.Optional;

import com.kamila.food.domain.model.Usuario;

public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long>{

	Optional<Usuario> findByEmail(String email);

}
