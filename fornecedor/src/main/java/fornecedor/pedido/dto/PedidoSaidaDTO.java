package fornecedor.pedido.dto;

import java.util.List;

public class PedidoSaidaDTO {
	private Long id;
	private Integer tempoDePreparo;
	private List<PedidoEnderecoItemDTO> enderecoItens;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTempoDePreparo() {
		return tempoDePreparo;
	}

	public void setTempoDePreparo(Integer tempoDePreparo) {
		this.tempoDePreparo = tempoDePreparo;
	}

	public List<PedidoEnderecoItemDTO> getEnderecoItens() {
		return enderecoItens;
	}

	public void setEnderecoItens(List<PedidoEnderecoItemDTO> enderecoItens) {
		this.enderecoItens = enderecoItens;
	}
}
