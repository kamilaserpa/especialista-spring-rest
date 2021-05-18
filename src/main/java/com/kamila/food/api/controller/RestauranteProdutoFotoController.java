package com.kamila.food.api.controller;

import com.kamila.food.api.assembler.FotoProdutoModelAssembler;
import com.kamila.food.api.model.FotoProdutoModel;
import com.kamila.food.api.model.input.FotoProdutoInput;
import com.kamila.food.domain.model.FotoProduto;
import com.kamila.food.domain.model.Produto;
import com.kamila.food.domain.service.CadastroProdutoService;
import com.kamila.food.domain.service.CatalogoFotoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/restaurantes/{idRestaurante}/produtos/{idProduto}/foto")
public class RestauranteProdutoFotoController {

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProdutoService;

    @Autowired
    private FotoProdutoModelAssembler fotoProdutoModelAssembler;

    @GetMapping
    public FotoProdutoModel buscar(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {
        FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(idRestaurante, idProduto);
        return fotoProdutoModelAssembler.toModel(fotoProduto);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoModel atualizarFoto(@PathVariable Long idRestaurante, @PathVariable Long idProduto,
                                          @Valid FotoProdutoInput fotoProdutoInput) throws IOException {

        Produto produto = cadastroProdutoService.buscarOuFalhar(idRestaurante, idProduto);

        MultipartFile arquivo = fotoProdutoInput.getArquivo();

        FotoProduto foto = new FotoProduto();
        foto.setProduto(produto);
        foto.setDescricao(fotoProdutoInput.getDescricao());
        foto.setContentType(arquivo.getContentType());
        foto.setTamanho(arquivo.getSize());
        foto.setNomeArquivo(arquivo.getOriginalFilename());

        FotoProduto fotoSalva = catalogoFotoProdutoService.salvar(foto, arquivo.getInputStream());

        return fotoProdutoModelAssembler.toModel(fotoSalva);
    }

}
