package loja.client.fornecedor.dto;

import java.util.List;

public class PedidoSaidaDTO {

	private Long id;
	private Integer tempoDePreparo;
	private List<PedidoEnderecoItemDTO> enderecoItens;

	public Integer getTempoDePreparo() {
		return tempoDePreparo;
	}

	public void setTempoDePreparo(Integer tempoDePreparo) {
		this.tempoDePreparo = tempoDePreparo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<PedidoEnderecoItemDTO> getEnderecoItens() {
		return enderecoItens;
	}

	public void setEnderecoItens(List<PedidoEnderecoItemDTO> enderecoItens) {
		this.enderecoItens = enderecoItens;
	}
}
