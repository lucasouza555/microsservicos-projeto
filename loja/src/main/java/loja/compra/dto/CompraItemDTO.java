package loja.compra.dto;

import java.math.BigDecimal;

public class CompraItemDTO {

	private Long id;
	private BigDecimal quantidade;

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
