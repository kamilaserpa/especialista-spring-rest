package com.kamila.food.client.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RestauranteModel {

    private Long id;
    private String nome;
    private BigDecimal precoFrete;
    private Boolean ativo;
    private Boolean aberto;

    private EnderecoModel endereco;
    private CozinhaModel cozinha;
}
