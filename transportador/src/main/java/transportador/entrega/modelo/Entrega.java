package transportador.entrega.modelo;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Entrega {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long pedidoId;
	
	private LocalDate dataParaBusca;
	
	private LocalDate previsaoParaEntrega;
	
	private String enderecoDestino;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "entrega")
	private List<EntregaOrigem> origens;
	
	public LocalDate getDataParaBusca() {
		return dataParaBusca;
	}

	public void setDataParaBusca(LocalDate dataParaBusca) {
		this.dataParaBusca = dataParaBusca;
	}

	public LocalDate getPrevisaoParaEntrega() {
		return previsaoParaEntrega;
	}

	public void setPrevisaoParaEntrega(LocalDate previsaoParaEntrega) {
		this.previsaoParaEntrega = previsaoParaEntrega;
	}

	public String getEnderecoDestino() {
		return enderecoDestino;
	}

	public void setEnderecoDestino(String enderecoDestino) {
		this.enderecoDestino = enderecoDestino;
	}

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<EntregaOrigem> getOrigens() {
		return origens;
	}

	public void setOrigens(List<EntregaOrigem> origens) {
		this.origens = origens;
	}
	
}
