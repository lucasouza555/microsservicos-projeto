package transportador.entrega;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import transportador.entrega.dto.EntregaDTO;
import transportador.entrega.dto.VoucherDTO;

@RestController
@RequestMapping("/entrega")
public class EntregaController {
	
	@Autowired
	private EntregaService entregaService;

	@PostMapping
	public VoucherDTO reservaEntrega(@RequestBody EntregaDTO pedidoDTO) {
		return entregaService.reservaEntrega(pedidoDTO);
	}
	
	@DeleteMapping("{id}")
	public void desfazReservaEntrega(@PathVariable("id") Long id) {
		entregaService.desfazReservaEntrega(id);
	}
}
