package com.kamila.food.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;

public interface FotoStorageService {

    void armazenar(NovaFoto novaFoto);

    @Builder
    @Getter
    class NovaFoto {

        private String nomeArquivo;
        private InputStream inputStream;

    }
}
