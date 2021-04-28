package loja.client.fornecedor;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import loja.client.fornecedor.dto.FornecedorInfoDTO;
import loja.compra.dto.CompraItemDTO;
import loja.pedido.dto.PedidoInfoDTO;

@FeignClient("fornecedor")
public interface FornecedorClient {
	
	@GetMapping("/info/{estado}")
	FornecedorInfoDTO getInfo(@PathVariable("estado") String estado);
	
	@PostMapping("/pedido")
	PedidoInfoDTO realizaPedido(List<CompraItemDTO> itens); 
}
