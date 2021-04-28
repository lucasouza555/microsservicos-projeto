package fornecedor.pedido;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fornecedor.pedido.dto.PedidoItemDTO;
import fornecedor.pedido.modelo.Pedido;

@RestController
@RequestMapping("pedido")
public class PedidoController {
	
	private static final Logger LOG = LoggerFactory.getLogger(PedidoController.class);
	
			@Autowired
	private PedidoService pedidoService;
	
	@RequestMapping(method = RequestMethod.POST)
	public Pedido realizaPedido(@RequestBody List<PedidoItemDTO> produtos) {
		LOG.info("Realizando pedido");
		return pedidoService.realizaPedido(produtos);
	}
	
	@RequestMapping("/{id}")
	public Pedido getPedidoPorId(@PathVariable Long id) {
		return pedidoService.getPedidoPorId(id);
	}
}