package loja.client.transportador.dto;

import java.time.LocalDate;
import java.util.List;

public class EntregaDTO {

	private Long pedidoId;
	
	private LocalDate dataParaEntrega;
	
	private String enderecoDestino;
	
	private List<EntregaOrigemDTO> origens;
	
	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public LocalDate getDataParaEntrega() {
		return dataParaEntrega;
	}

	public void setDataParaEntrega(LocalDate dataParaEntrega) {
		this.dataParaEntrega = dataParaEntrega;
	}

	public String getEnderecoDestino() {
		return enderecoDestino;
	}

	public void setEnderecoDestino(String enderecoDestino) {
		this.enderecoDestino = enderecoDestino;
	}

	public List<EntregaOrigemDTO> getOrigens() {
		return origens;
	}

	public void setOrigens(List<EntregaOrigemDTO> origens) {
		this.origens = origens;
	}
}

