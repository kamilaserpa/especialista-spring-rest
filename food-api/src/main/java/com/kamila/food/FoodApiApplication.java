package com.kamila.food;

import java.util.TimeZone;

import com.kamila.food.core.io.Base64ProtocolResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.kamila.food.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class FoodApiApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		var app = new SpringApplication(FoodApiApplication.class); // Instancia aplicação
		app.addListeners(new Base64ProtocolResolver()); // Adiciona Listener
		app.run(args); // Inicializa

//		SpringApplication.run(FoodApiApplication.class, args);
	}

}
