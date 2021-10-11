package com.kamila.food.core.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface CheckSecurity {

    public @interface Cozinhas {

        @PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
        @Retention(RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeEditar {
        }

        @PreAuthorize("isAuthenticated()")
        @Retention(RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeConsultar {
        }

    }

}
