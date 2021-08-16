package com.kamila.food.api.v1.controller;

import com.kamila.food.api.v1.FoodLinks;
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

    @GetMapping
    public RootEntryPointModel root() {
        var rootEntrypointModel = new RootEntryPointModel();

        rootEntrypointModel.add(foodLinks.linkToCozinhas("cozinhas"));
        rootEntrypointModel.add(foodLinks.linkToPedidos("pedidos"));
        rootEntrypointModel.add(foodLinks.linkToRestaurantes("restaurantes"));
        rootEntrypointModel.add(foodLinks.linkToGrupos("grupos"));
        rootEntrypointModel.add(foodLinks.linkToUsuarios("usuarios"));
        rootEntrypointModel.add(foodLinks.linkToPermissoes("permissoes"));
        rootEntrypointModel.add(foodLinks.linkToFormasPagamento("formas-pagamento"));
        rootEntrypointModel.add(foodLinks.linkToEstados("estados"));
        rootEntrypointModel.add(foodLinks.linkToCidades("cidades"));
        rootEntrypointModel.add(foodLinks.linkToEstatisticas("estatisticas"));

        return rootEntrypointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
    }

}
