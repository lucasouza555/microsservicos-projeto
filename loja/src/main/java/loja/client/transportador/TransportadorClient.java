package loja.client.transportador;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import loja.client.transportador.dto.EntregaDTO;
import loja.client.transportador.dto.VoucherDTO;

@FeignClient("transportador")
public interface TransportadorClient {
	
	@PostMapping("/entrega")
	public VoucherDTO reservaEntrega(@RequestBody EntregaDTO entregaDTO);
		
}
