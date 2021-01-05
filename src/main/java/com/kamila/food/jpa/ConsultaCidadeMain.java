package com.kamila.food.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.kamila.food.FoodApiApplication;
import com.kamila.food.domain.model.Cidade;
import com.kamila.food.domain.repository.CidadeRepository;

/*
 * Classe main criada para ser executada como JavaApplication, a fim de teste de findAll
 */
public class ConsultaCidadeMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(FoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		CidadeRepository cidadeRepository = applicationContext.getBean(CidadeRepository.class);

		List<Cidade> todasCidades = cidadeRepository.listar();

		for (Cidade cidade : todasCidades) {
			System.out.printf("%s - %s\n", cidade.getNmCidade(), cidade.getEstado().getNmEstado());
		}
	}

}
