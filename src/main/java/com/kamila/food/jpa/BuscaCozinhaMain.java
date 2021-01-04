package com.kamila.food.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.kamila.food.FoodApiApplication;
import com.kamila.food.domain.model.Cozinha;

/*
 * Classe main criada para ser esecutada como JavaApplication, a fim de teste
 */
public class BuscaCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(FoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		CadastroCozinha cadastroCozinha = applicationContext.getBean(CadastroCozinha.class);

		Cozinha cozinha = cadastroCozinha.buscar(1L);

		System.out.println(cozinha.getNmCozinha());
	}

}
