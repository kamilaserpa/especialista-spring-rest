package com.kamila.food.core.security;

import com.kamila.food.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class FoodSecurity {

    @Autowired
    private RestauranteRepository restauranteRepository;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUsuarioId() {
        // Retorna uma intancia de AuthUser encriptado
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();
        // Captura claim "usuario_id" do token
        return jwt.getClaim("usuario_id");
    }

    // Verifica se usuário é responsável pelo Restaurante
    public boolean gerenciaRestaurante(Long idRestaurante) {
        return restauranteRepository.existsResponsavel(idRestaurante, getUsuarioId());
    }

}
