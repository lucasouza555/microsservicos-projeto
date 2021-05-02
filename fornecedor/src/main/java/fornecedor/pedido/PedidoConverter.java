package fornecedor.pedido;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fornecedor.pedido.dto.PedidoEnderecoItemDTO;
import fornecedor.pedido.dto.PedidoSaidaDTO;
import fornecedor.pedido.modelo.Pedido;
import fornecedor.pedido.modelo.PedidoItem;

public final class PedidoConverter {

	public static PedidoSaidaDTO toDto(Pedido pedido) {
		PedidoSaidaDTO saida = new PedidoSaidaDTO();
		saida.setId(pedido.getId());
		saida.setTempoDePreparo(pedido.getTempoDePreparo());
		saida.setEnderecoItens(pedido.getItens().stream().map(item -> {
			PedidoEnderecoItemDTO itemDto = new PedidoEnderecoItemDTO();
			itemDto.setProdutoId(item.getProduto().getId());
			itemDto.setEndereco(item.getProduto().getFornecedor().toString());
			return itemDto;
		}).collect(Collectors.toList()));
		
		return saida;
	}

	public static List<PedidoEnderecoItemDTO> toDto(List<PedidoItem> itens) {
		List<PedidoEnderecoItemDTO> enderecos = new ArrayList<>();
		
		if(itens != null && !itens.isEmpty()) {
			enderecos = itens.stream().map(item -> {
				PedidoEnderecoItemDTO itemDto = new PedidoEnderecoItemDTO();
				itemDto.setProdutoId(item.getProduto().getId());
				itemDto.setEndereco(item.getProduto().getFornecedor().toString());
				return itemDto;
			}).collect(Collectors.toList());
		}
		
		return enderecos;
	}

}
