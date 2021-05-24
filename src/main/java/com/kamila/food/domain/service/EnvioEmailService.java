package com.kamila.food.domain.service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public interface EnvioEmailService {

    void enviar(Mensagem mensagem);

    @Getter
    @Builder
    class Mensagem {

        @Singular
        private Set<String> destinatarios; // Set para que não haja elementos repetidos

        @NonNull // Caso nullo chama Exception na tentativa de criar objeto Mensagem
        private String assunto;

        @NonNull
        private String corpo;

        @Singular("variavel") // Nome atribuído ao singular de "variaveis"
        private Map<String, Object> variaveis;

    }

}
