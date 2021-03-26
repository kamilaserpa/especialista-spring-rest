package com.kamila.food.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kamila.food.domain.exception.NegocioException;
import com.kamila.food.domain.exception.PedidoNaoEncontradoException;
import com.kamila.food.domain.model.Cidade;
import com.kamila.food.domain.model.FormaPagamento;
import com.kamila.food.domain.model.Pedido;
import com.kamila.food.domain.model.Produto;
import com.kamila.food.domain.model.Restaurante;
import com.kamila.food.domain.model.Usuario;
import com.kamila.food.domain.repository.PedidoRepository;

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

	public Pedido buscarOuFalhar(Long idPedido) {
		return pedidoRepository.findById(idPedido)
				.orElseThrow(() -> new PedidoNaoEncontradoException(idPedido));
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
