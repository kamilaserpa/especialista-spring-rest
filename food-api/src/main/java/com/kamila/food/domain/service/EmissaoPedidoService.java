package com.kamila.food.domain.service;

import com.kamila.food.domain.exception.NegocioException;
import com.kamila.food.domain.exception.PedidoNaoEncontradoException;
import com.kamila.food.domain.filter.PedidoFilter;
import com.kamila.food.domain.model.*;
import com.kamila.food.domain.repository.PedidoRepository;
import com.kamila.food.infrastructure.repository.spec.PedidoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmissaoPedidoService {

	@Autowired
	PedidoRepository pedidoRepository;
	
	@Autowired
	CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	@Autowired
	CadastroProdutoService cadastroProdutoService;

	public List<Pedido> findAll() {
		return pedidoRepository.findAll();
	}
	
	public List<Pedido> findAll(PedidoFilter filtro) {
		return pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro));
	}
	
	public Page<Pedido> findAll(PedidoFilter filtro, Pageable pageable) {
		return pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
	}

	public Pedido buscarOuFalhar(String codigoPedido) {
		return pedidoRepository.findByCodigo(codigoPedido)
				.orElseThrow(() -> new PedidoNaoEncontradoException(codigoPedido));
	}

	@Transactional
	public Pedido emitir(Pedido pedido) {
	    validarPedido(pedido);
	    validarItens(pedido);

	    pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
	    pedido.calcularValorTotal();

	    return pedidoRepository.save(pedido);
	}

	private void validarPedido(Pedido pedido) {
	    Cidade cidade = cadastroCidadeService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
	    Usuario cliente = cadastroUsuarioService.buscarOuFalhar(pedido.getCliente().getId());
	    Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
	    FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());

	    pedido.getEnderecoEntrega().setCidade(cidade);
	    pedido.setCliente(cliente);
	    pedido.setRestaurante(restaurante);
	    pedido.setFormaPagamento(formaPagamento);
	    
	    if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
	        throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
	                formaPagamento.getDescricao()));
	    }
	}
	
	private void validarItens(Pedido pedido) {
	    pedido.getItens().forEach(item -> {
	        Produto produto = cadastroProdutoService.buscarOuFalhar(
	                pedido.getRestaurante().getId(), item.getProduto().getId());
	        
	        item.setPedido(pedido);
	        item.setProduto(produto);
	        item.setPrecoUnitario(produto.getPreco());
	    });
	}
	
}
