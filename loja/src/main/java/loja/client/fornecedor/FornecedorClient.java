package loja.client.fornecedor;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import loja.client.fornecedor.dto.PedidoEnderecoItemDTO;
import loja.client.fornecedor.dto.PedidoSaidaDTO;
import loja.compra.dto.CompraItemDTO;

@FeignClient("fornecedor")
public interface FornecedorClient {
	
	@PostMapping("/pedidos")
	PedidoSaidaDTO realizaPedido(List<CompraItemDTO> itens);
	
	@DeleteMapping("/pedidos/{id}")
	void desfazPedido(@PathVariable("id") Long id);
	
	@GetMapping("/pedidos/{id}/enderecos")
	List<PedidoEnderecoItemDTO> getEnderecoItens(@PathVariable("id") Long id);
}


