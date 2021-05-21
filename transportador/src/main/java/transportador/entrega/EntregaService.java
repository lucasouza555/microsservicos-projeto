package transportador.entrega;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import transportador.entrega.dto.EntregaDTO;
import transportador.entrega.dto.VoucherDTO;
import transportador.entrega.modelo.Entrega;
import transportador.entrega.modelo.EntregaOrigem;

@Service
public class EntregaService {
	
	private static final Logger LOG = LoggerFactory.getLogger(EntregaService.class);
	
	@Autowired
	private EntregaRepository repository;

	public VoucherDTO reservaEntrega(EntregaDTO pedidoDTO) {
		LOG.info("Realizando reserva da entrega");
		Entrega entrega = new Entrega();
		entrega.setDataParaBusca(pedidoDTO.getDataParaEntrega());
		entrega.setPrevisaoParaEntrega(pedidoDTO.getDataParaEntrega().plusDays(1l));
		entrega.setEnderecoDestino(pedidoDTO.getEnderecoDestino());
		entrega.setOrigens(pedidoDTO.getOrigens().stream().map(origemDto -> {
			EntregaOrigem origem = new EntregaOrigem();
			origem.setEntrega(entrega);
			origem.setProdutoId(origemDto.getProdutoId());
			origem.setEndereco(origemDto.getEndereco());
			return origem;
		}).collect(Collectors.toList()));
		entrega.setPedidoId(pedidoDTO.getPedidoId());
		
		repository.save(entrega);
		
		VoucherDTO voucher = new VoucherDTO();
		voucher.setNumero(entrega.getId());
		voucher.setPrevisaoParaEntrega(entrega.getPrevisaoParaEntrega());
		return voucher;
	}

	public void desfazReservaEntrega(Long id) {
		Entrega entrega = repository.findById(id).orElse(null);
		
		if(entrega != null) {
			repository.delete(entrega);	
		}
	}
}
