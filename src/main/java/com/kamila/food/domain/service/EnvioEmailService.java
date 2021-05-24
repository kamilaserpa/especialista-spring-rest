package com.kamila.food.domain.service;

import lombok.Builder;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface EnvioEmailService {

    void enviar(Mensagem mensagem);

    @Getter
    @Builder
    class Mensagem {
        private Set<String> destinatarios;
        private String assunto;
        private String corpo;
    }

}
