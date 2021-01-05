package com.kamila.food.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.kamila.food.FoodApiApplication;
import com.kamila.food.domain.model.Permissao;
import com.kamila.food.domain.repository.PermissaoRepository;

/*
 * Classe main criada para ser executada como JavaApplication, a fim de teste de findAll
 */
public class ConsultaPermissaoMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(FoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
        
        PermissaoRepository permissaoRepository = applicationContext.getBean(PermissaoRepository.class);
        
        List<Permissao> todasPermissoes = permissaoRepository.listar();
        
        for (Permissao permissao : todasPermissoes) {
            System.out.printf("%s - %s\n", permissao.getNmPermissao(), permissao.getDescricao());
        }
    }
    
}
