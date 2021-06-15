package com.kamila.food.client;

import com.kamila.food.client.api.ClientApiException;
import com.kamila.food.client.api.RestauranteClient;
import com.kamila.food.client.model.RestauranteModel;
import com.kamila.food.client.model.input.CidadeIdInput;
import com.kamila.food.client.model.input.CozinhaIdInput;
import com.kamila.food.client.model.input.EnderecoInput;
import com.kamila.food.client.model.input.RestauranteInput;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class InclusaoRestauranteMain {

    public static void main(String[] args) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            RestauranteClient restauranteClient = new RestauranteClient(
                    restTemplate, "http://localhost:8080"
            );

            var cidade = new CidadeIdInput();
            cidade.setId(1L);

            var cozinha = new CozinhaIdInput();
            cozinha.setId(1L);

            var endereco = new EnderecoInput();
            endereco.setCidade(cidade);
            endereco.setCep("38500-111");
            endereco.setLogradouro("Rua Abc");
            endereco.setNumero("500");
            endereco.setBairro("Centro");

            var restaurante = new RestauranteInput();
//            restaurante.setNome("La pasta");
//            restaurante.setTaxaFrete(new BigDecimal(12.5));
            restaurante.setCozinha(cozinha);
            restaurante.setCozinha(cozinha);
            restaurante.setEndereco(endereco);

            RestauranteModel restauranteModel = restauranteClient.adicionar(restaurante);

            System.out.println(restauranteModel);

        } catch (ClientApiException e) {
            if (e.getProblem() != null) {
                System.out.println(e.getProblem().getUserMessage());

                e.getProblem().getObjects().stream()
                        .forEach(p -> System.out.println("- " + p.getUserMessage()));

            } else {
                System.out.println("Erro desconhecido.");
                e.printStackTrace();
            }
        }
    }
}
