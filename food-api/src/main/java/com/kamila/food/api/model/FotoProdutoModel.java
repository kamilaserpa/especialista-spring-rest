package com.kamila.food.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FotoProdutoModel {

    @ApiModelProperty(example = "c62c589a-5623-48d8-833f-a2a635016b17_espeto-cupim.jpg")
    private String nomeArquivo;

    @ApiModelProperty(example = "Prime Rib ao ponto")
    private String descricao;

    @ApiModelProperty(example = "image/jpeg")
    private String contentType;

    @ApiModelProperty(example = "202912")
    private Long tamanho;

}
