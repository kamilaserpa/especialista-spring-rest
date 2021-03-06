package com.kamila.food.api.v1.controller;

import com.kamila.food.api.v1.assembler.RestauranteApenasNomeModelAssembler;
import com.kamila.food.api.v1.assembler.RestauranteBasicoModelAssembler;
import com.kamila.food.api.v1.assembler.RestauranteInputDisassembler;
import com.kamila.food.api.v1.assembler.RestauranteModelAssembler;
import com.kamila.food.api.v1.model.RestauranteApenasNomeModel;
import com.kamila.food.api.v1.model.RestauranteBasicoModel;
import com.kamila.food.api.v1.model.RestauranteModel;
import com.kamila.food.api.v1.model.input.RestauranteInput;
import com.kamila.food.api.v1.openapi.controller.RestauranteControllerOpenApi;
import com.kamila.food.core.security.CheckSecurity;
import com.kamila.food.domain.exception.CidadeNaoEncontradaException;
import com.kamila.food.domain.exception.CozinhaNaoEncontradaException;
import com.kamila.food.domain.exception.NegocioException;
import com.kamila.food.domain.exception.RestauranteNaoEncontradoException;
import com.kamila.food.domain.model.Restaurante;
import com.kamila.food.domain.repository.RestauranteRepository;
import com.kamila.food.domain.service.CadastroRestauranteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/v1/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

//	@Autowired
//	private SmartValidator validator;

    @Autowired
    private RestauranteModelAssembler restauranteModelAssembler;

    @Autowired
    private RestauranteApenasNomeModelAssembler restauranteApenasNomeModelAssembler;

    @Autowired
    private RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;

    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;

//	@GetMapping
//	public MappingJacksonValue listar(@RequestParam(required = false) String projecao) {
//		List<Restaurante> restaurantes = restauranteRepository.findAll();
//		List<RestauranteModel> restaurantesModel = restauranteModelAssembler.toCollectionModel(restaurantes);
//
//		MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restaurantesModel);
//		restaurantesWrapper.setSerializationView(RestauranteView.Resumo.class);
//
//		if ("apenas-nome".equals(projecao)) { // para evitar NullPointerException caso parametro n??o recebido
//			restaurantesWrapper.setSerializationView(RestauranteView.ApenasNome.class);
//		} else if ("completo".equals(projecao)) {
//			restaurantesWrapper.setSerializationView(null);
//		}
//
//		return restaurantesWrapper;
//	}

    // Dois m??todos semelhantes com serializa????o resumida dependente do par??metro "projecao"
//    @JsonView(RestauranteView.Resumo.class)
    @CheckSecurity.Restaurantes.PodeConsultar
    @Override
    @GetMapping
    public CollectionModel<RestauranteBasicoModel> listar() {
        log.info("Log before query");
        List<Restaurante> restaurantes = restauranteRepository.findAll();

        return restauranteBasicoModelAssembler.toCollectionModel(restaurantes);
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @Override
//    @JsonView(RestauranteView.ApenasNome.class)
    @GetMapping(params = "projecao=apenas-nome")
    public CollectionModel<RestauranteApenasNomeModel> listarApenasNomes() {
        return restauranteApenasNomeModelAssembler.toCollectionModel(restauranteRepository.findAll());
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @Override
    @GetMapping("/{idRestaurante}")
    public RestauranteModel buscar(@PathVariable Long idRestaurante) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(idRestaurante);
        return restauranteModelAssembler.toModel(restaurante);
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel salvar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
            return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @Override
    @PutMapping("/{idRestaurante}")
    @ResponseStatus(HttpStatus.OK)
    public RestauranteModel atualizar(@PathVariable Long idRestaurante, @RequestBody @Valid RestauranteInput restauranteInput) {
        try {

            Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(idRestaurante);

            restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

            return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @Override
    @DeleteMapping("/{idRestaurante}")
    public void remover(@PathVariable Long idRestaurante) {
        cadastroRestauranteService.remover(idRestaurante);
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @Override
    @PutMapping("/{idRestaurante}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable Long idRestaurante) {
        // Op????o por PUT por ser um m??todo idenpotente, ainda que executado v??rias vezes provoca o mesmo resultado
        cadastroRestauranteService.ativar(idRestaurante);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @Override
    @DeleteMapping("/{idRestaurante}/inativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable Long idRestaurante) {
        cadastroRestauranteService.inativar(idRestaurante);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @Override
    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> idsRestaurantes) {
        try {
            cadastroRestauranteService.ativar(idsRestaurantes);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @Override
    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> idsRestaurantes) {
        try {
            cadastroRestauranteService.inativar(idsRestaurantes);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @Override
    @PutMapping("/{idRestaurante}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> abrir(@PathVariable Long idRestaurante) {
        cadastroRestauranteService.abrir(idRestaurante);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @Override
    @PutMapping("/{idRestaurante}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> fechar(@PathVariable Long idRestaurante) {
        cadastroRestauranteService.fechar(idRestaurante);

        return ResponseEntity.noContent().build();
    }
	
	/*
   // Comentando m??todo de atualiza????o parcial, pelo visto de que n??o ?? interessante mant??-lo por hora.
	@PatchMapping("/{idRestaurante}")
	@ResponseStatus(HttpStatus.OK)
	public RestauranteModel atualizarParcial(@PathVariable Long idRestaurante, @RequestBody Map<String, Object> campos,
			HttpServletRequest request) {

		Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(idRestaurante);

		merge(campos, restauranteAtual, request);
		validate(restauranteAtual, "restaurante");
		
		return atualizar(idRestaurante, restauranteAtual);
	}

	private void validate(Restaurante restaurante, String objectName) {
		
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
		// Aciona valida????o em objecto programaticamente
		validator.validate(restaurante, bindingResult);
		
		if (bindingResult.hasErrors()) {
			throw new ValidacaoException(bindingResult);
		}
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {
		ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(request);
		
		try {	
			// Utilizando  ObjectMapper para criar objeto tipado Restaurante
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true); // Lan??a exce????o caso recebeu objeto n??o desserializ??vel
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true); // Padr??o do ObjectMapper
			
			Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
			
			dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
				// findField: Retorna instancia de um campo
				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				field.setAccessible(true);
				
				// GetField: Captura o valor do campo
				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
				
				ReflectionUtils.setField(field, restauranteDestino, novoValor);
			});
		} catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, servletServerHttpRequest);
		}
	}
	*/

}
