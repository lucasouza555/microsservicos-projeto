package loja.compra.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CompraDTO {
	
	@JsonIgnore
	private Long compraId;
	
	private List<CompraItemDTO> itens;
	private EnderecoDTO endereco;
	
	public List<CompraItemDTO> getItens() {
		return itens;
	}
	public void setItens(List<CompraItemDTO> itens) {
		this.itens = itens;
	}
	public EnderecoDTO getEndereco() {
		return endereco;
	}
	public void setEndereco(EnderecoDTO endereco) {
		this.endereco = endereco;
	}
	public Long getCompraId() {
		return compraId;
	}
	public void setCompraId(Long compraId) {
		this.compraId = compraId;
	}
}
