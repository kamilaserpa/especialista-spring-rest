package com.kamila.foodauth.core;

import com.kamila.foodauth.domain.Usuario;
import com.kamila.foodauth.domain.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
 * Spring Security usa esta implementação para consultar usuários
 */
@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("Usuário com e-mail %s não foi encontrado.", username))
                );

        return new AuthUser(usuario);
    }

}
