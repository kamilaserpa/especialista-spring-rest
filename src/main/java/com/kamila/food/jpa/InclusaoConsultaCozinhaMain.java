package com.kamila.food.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.kamila.food.FoodApiApplication;
import com.kamila.food.domain.model.Cozinha;

/*
 * Classe main criada para ser esecutada como JavaApplication, a fim de teste
 */
public class InclusaoConsultaCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(FoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		CadastroCozinha cadastroCozinha = applicationContext.getBean(CadastroCozinha.class);

		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNmCozinha("Brasileira");

		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNmCozinha("Japonesa");

		cozinha1 = cadastroCozinha.adicionar(cozinha1);
		cozinha2 = cadastroCozinha.adicionar(cozinha2);

		// Visualizando retorno do método EntityManager.merge com objeto persistido
		System.out.printf("%d - %s\n", cozinha1.getIdCozinha(), cozinha1.getNmCozinha());
		System.out.printf("%d - %s\n", cozinha2.getIdCozinha(), cozinha2.getNmCozinha());
	}

}
