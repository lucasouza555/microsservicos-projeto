package fornecedor.pedido;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fornecedor.pedido.dto.PedidoSaidaDTO;
import fornecedor.pedido.dto.PedidoEnderecoItemDTO;
import fornecedor.pedido.dto.PedidoItemDTO;
import fornecedor.pedido.modelo.Pedido;

@RestController
@RequestMapping("pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoService pedidoService;
	
	@PostMapping
	public PedidoSaidaDTO realizaPedido(@RequestBody List<PedidoItemDTO> produtos) {
		Pedido pedido = pedidoService.realizaPedido(produtos);
		PedidoSaidaDTO dto = PedidoConverter.toDto(pedido);
		return dto;
	}
	
	@DeleteMapping("/{id}")
	public void desfazPedido(@PathVariable("id") Long id) {
		pedidoService.desfazPedido(id);
	}
	
	@GetMapping("/{id}/enderecos")
	public List<PedidoEnderecoItemDTO> getEnderecoItens(@PathVariable("id") Long id) {
		Pedido pedido = pedidoService.getPedidoPorId(id);
		List<PedidoEnderecoItemDTO> enderecos = PedidoConverter.toDto(pedido.getItens());
		return enderecos;
	}
}
