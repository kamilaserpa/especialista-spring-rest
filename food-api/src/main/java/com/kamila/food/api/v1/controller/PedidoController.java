package com.kamila.food.api.v1.controller;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.kamila.food.api.v1.assembler.PedidoInputDisassembler;
import com.kamila.food.api.v1.assembler.PedidoModelAssembler;
import com.kamila.food.api.v1.assembler.PedidoResumoModelAssembler;
import com.kamila.food.api.v1.model.PedidoModel;
import com.kamila.food.api.v1.model.PedidoResumoModel;
import com.kamila.food.api.v1.model.input.PedidoInput;
import com.kamila.food.api.v1.openapi.controller.PedidoControllerOpenApi;
import com.kamila.food.core.data.PageWrapper;
import com.kamila.food.core.data.PageableTranslator;
import com.kamila.food.domain.exception.EntidadeNaoEncontradaException;
import com.kamila.food.domain.exception.NegocioException;
import com.kamila.food.domain.filter.PedidoFilter;
import com.kamila.food.domain.model.Pedido;
import com.kamila.food.domain.model.Usuario;
import com.kamila.food.domain.service.CadastroProdutoService;
import com.kamila.food.domain.service.EmissaoPedidoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

    @Autowired
    EmissaoPedidoService emissaoPedidoService;

    @Autowired
    CadastroProdutoService cadastroProdutoService;

    @Autowired
    PedidoModelAssembler pedidoModelAssembler;

    @Autowired
    PedidoResumoModelAssembler pedidoResumoModelAssembler;

    @Autowired
    PedidoInputDisassembler pedidoInputDisassembler;

    @Autowired
    private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

    @Override
    @GetMapping
    public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable) {

        Pageable pageableTraduzido = traduzirPageable(pageable);

        Page<Pedido> pedidosPage = emissaoPedidoService.findAll(filtro, pageableTraduzido);

        // Para que Hateoas adicione o link com o nome da propriedade em sort corretamente
        pedidosPage = new PageWrapper<>(pedidosPage, pageable);

        return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoModelAssembler);
    }

    @Override
    @GetMapping("/filter")
    public MappingJacksonValue listarPedidosFilter(@RequestParam(required = false) String campos) {
        List<Pedido> pedidos = emissaoPedidoService.findAll();
        CollectionModel<PedidoResumoModel> pedidosModel = pedidoResumoModelAssembler.toCollectionModel(pedidos);
        // Instancia envelope de pedidos
        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        // Retorna apenas as propriedades mencionadas, filtra as demais
        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());

        if (StringUtils.isNotBlank(campos)) {
            filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
        }

        pedidosWrapper.setFilters(filterProvider);
        return pedidosWrapper;
    }

    @Override
    @GetMapping("/{codigoPedido}")
    public PedidoModel buscar(@PathVariable String codigoPedido) {
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);

        return pedidoModelAssembler.toModel(pedido);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

            // TODO pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            novoPedido = emissaoPedidoService.emitir(novoPedido);

            return pedidoModelAssembler.toModel(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    /**
     * Convertendo propriedade recebida no Pageable para outra nomenclatura.
     * Converte nomeCliente, em propriedade do modelo de domínio Pedido "cliente.nome".
     * GET /pedidos?sort=nomeCliente
     * GET /pedidos?sort=cliente.nome
     */
    private Pageable traduzirPageable(Pageable apiPageable) {

        // Atributos pelos quais a paginação é ordenável
        var mapeamento = Map.of(
                "codigo", "codigo",
                "subtotal", "subtotal",
                "taxaFrete", "taxaFrete",
                "valorTotal", "valorTotal",
                "dataCriacao", "dataCriacao",
                "nomeRestaurante", "restaurante.nome", // pois o retorno possui essa estrutura sendo mais intuitivo para o consumidor
                "restaurante.id", "restaurante.id",
                "nomeCliente", "cliente.nome"
        );

        return PageableTranslator.translate(apiPageable, mapeamento);
    }

}
