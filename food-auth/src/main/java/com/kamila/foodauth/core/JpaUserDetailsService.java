package com.kamila.foodauth.core;

import com.kamila.foodauth.domain.Usuario;
import com.kamila.foodauth.domain.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

/*
 * Spring Security usa esta implementação para consultar usuários
 */
@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true) // Pois Spring estava fechando a transação após o findByEmail, exception ao buscar grupos em getAuthorities
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("Usuário com e-mail %s não foi encontrado.", username))
                );

        return new AuthUser(usuario, getAuthorities(usuario));
    }

    /**
     * Retorna permissões de usuário capturadas do banco de dados
     */
    private Collection<GrantedAuthority> getAuthorities(Usuario usuario) {
        return usuario.getGrupos().stream()
                .flatMap(grupo -> grupo.getPermissoes().stream())
                .map(permissao -> new SimpleGrantedAuthority(permissao.getNome().toUpperCase()))
                .collect(Collectors.toSet());
        // Set não permite valores duplicados, caso o usuario esteja em mais de um grupo existiria essa possibilidade
    }

}
