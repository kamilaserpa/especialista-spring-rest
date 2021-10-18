package com.kamila.food.api.v1.controller;

import com.kamila.food.api.v1.FoodLinks;
import com.kamila.food.core.security.FoodSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @Autowired
    private FoodLinks foodLinks;

    @Autowired
    private FoodSecurity foodSecurity;

    @GetMapping
    public RootEntryPointModel root() {
        var rootEntryPointModel = new RootEntryPointModel();

        if (foodSecurity.podeConsultarCozinhas()) {
            rootEntryPointModel.add(foodLinks.linkToCozinhas("cozinhas"));
        }

        if (foodSecurity.podePesquisarPedidos()) {
            rootEntryPointModel.add(foodLinks.linkToPedidos("pedidos"));
        }

        if (foodSecurity.podeConsultarRestaurantes()) {
            rootEntryPointModel.add(foodLinks.linkToRestaurantes("restaurantes"));
        }

        if (foodSecurity.podeConsultarUsuariosGruposPermissoes()) {
            rootEntryPointModel.add(foodLinks.linkToGrupos("grupos"));
            rootEntryPointModel.add(foodLinks.linkToUsuarios("usuarios"));
            rootEntryPointModel.add(foodLinks.linkToPermissoes("permissoes"));
        }

        if (foodSecurity.podeConsultarFormasPagamento()) {
            rootEntryPointModel.add(foodLinks.linkToFormasPagamento("formas-pagamento"));
        }

        if (foodSecurity.podeConsultarEstados()) {
            rootEntryPointModel.add(foodLinks.linkToEstados("estados"));
        }

        if (foodSecurity.podeConsultarCidades()) {
            rootEntryPointModel.add(foodLinks.linkToCidades("cidades"));
        }

        if (foodSecurity.podeConsultarEstatisticas()) {
            rootEntryPointModel.add(foodLinks.linkToEstatisticas("estatisticas"));
        }

        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
    }

}
