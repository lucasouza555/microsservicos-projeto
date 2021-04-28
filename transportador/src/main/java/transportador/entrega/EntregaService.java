package transportador.entrega;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import transportador.entrega.dto.EntregaDTO;
import transportador.entrega.dto.VoucherDTO;
import transportador.entrega.modelo.Entrega;

@Service
public class EntregaService {
	
	@Autowired
	private EntregaRepository repository;

	public VoucherDTO reservaEntrega(EntregaDTO pedidoDTO) {
		
		Entrega entrega = new Entrega();
		entrega.setDataParaBusca(pedidoDTO.getDataParaEntrega());
		entrega.setPrevisaoParaEntrega(pedidoDTO.getDataParaEntrega().plusDays(1l));
		entrega.setEnderecoDestino(pedidoDTO.getEnderecoDestino());
		entrega.setEnderecoOrigem(pedidoDTO.getEnderecoOrigem());
		entrega.setPedidoId(pedidoDTO.getPedidoId());
		
		repository.save(entrega);
		
		VoucherDTO voucher = new VoucherDTO();
		voucher.setNumero(entrega.getId());
		voucher.setPrevisaoParaEntrega(entrega.getPrevisaoParaEntrega());
		return voucher;
	}

}
