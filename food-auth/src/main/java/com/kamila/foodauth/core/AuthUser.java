package com.kamila.foodauth.core;

import com.kamila.foodauth.domain.Usuario;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class AuthUser extends User {

    private static final long serialVersionUID = 1L;

    private String fullName;

    public AuthUser(Usuario usuario) {
        super(usuario.getEmail(), usuario.getSenha(), Collections.emptyList());

        this.fullName = usuario.getNome();
    }

}
