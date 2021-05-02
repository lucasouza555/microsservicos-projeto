package fornecedor.pedido;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fornecedor.pedido.dto.PedidoItemDTO;
import fornecedor.pedido.modelo.Pedido;
import fornecedor.pedido.modelo.PedidoItem;
import fornecedor.pedido.modelo.PedidoStatus;
import fornecedor.produto.ProdutoRepository;
import fornecedor.produto.modelo.Produto;

@Service
public class PedidoService {
	
	private static final Logger LOG = LoggerFactory.getLogger(PedidoService.class);
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;

	public Pedido realizaPedido(List<PedidoItemDTO> itens) {
		LOG.info("Realizando pedido");
		
		if(itens == null) {
			return null;
		}
		
		Pedido pedido = new Pedido();
		List<PedidoItem> pedidoItens = toPedidoItem(pedido, itens);
		pedido.setItens(pedidoItens);
		pedido.setStatus(PedidoStatus.RECEBIDO);
		pedido.setTempoDePreparo(itens.size());
		return pedidoRepository.save(pedido);
	}
	
	public Pedido getPedidoPorId(Long id) {
		return pedidoRepository.findById(id).orElse(new Pedido());
	}
	
	public void desfazPedido(Long id) {
		Pedido pedido = pedidoRepository.findById(id).orElse(null);
		
		if(pedido != null) {
			pedidoRepository.delete(pedido);	
		}
	}

	private List<PedidoItem> toPedidoItem(Pedido pedido, List<PedidoItemDTO> itens) {		
		List<Long> idsProdutos = itens
				.stream()
				.map(item -> item.getId())
				.collect(Collectors.toList());
		
		List<Produto> produtosDoPedido = produtoRepository.findByIdIn(idsProdutos);
		
		List<PedidoItem> pedidoItens = itens
			.stream()
			.map(item -> {
				Produto produto = produtosDoPedido
						.stream()
						.filter(p -> p.getId() == item.getId())
						.findFirst().get();
				
				PedidoItem pedidoItem = new PedidoItem();
				pedidoItem.setPedido(pedido);
				pedidoItem.setProduto(produto);
				pedidoItem.setQuantidade(item.getQuantidade());
				return pedidoItem;
			})
			.collect(Collectors.toList());
		return pedidoItens;
	}
}
