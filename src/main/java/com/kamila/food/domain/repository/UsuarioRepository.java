package com.kamila.food.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamila.food.domain.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
