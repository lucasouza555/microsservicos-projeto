package loja.compra;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import loja.client.fornecedor.FornecedorClient;
import loja.client.fornecedor.dto.PedidoEnderecoItemDTO;
import loja.client.fornecedor.dto.PedidoSaidaDTO;
import loja.client.transportador.TransportadorClient;
import loja.client.transportador.dto.EntregaDTO;
import loja.client.transportador.dto.EntregaOrigemDTO;
import loja.client.transportador.dto.VoucherDTO;
import loja.compra.dto.CompraDTO;
import loja.compra.modelo.Compra;
import loja.compra.modelo.CompraSituacao;

@Service
public class CompraService {
	
	private static final Logger LOG = LoggerFactory.getLogger(CompraService.class);
	
	private long inicio;
	
	@Autowired
	private FornecedorClient fornecedorClient;
	
	@Autowired
	private CompraRepository compraRepository;
		
	@Autowired
	private TransportadorClient transportadorClient;
	
	@HystrixCommand(threadPoolKey = "getCompraThreadPool")
	public Compra getCompra(Long id) {
		return compraRepository.findById(id).orElse(new Compra());
	}
	
	@HystrixCommand(threadPoolKey = "realizaCompraThreadPool", 
			fallbackMethod = "realizaCompraFallback", 
			commandProperties = {@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000")})
	public Compra reprocessar(CompraDTO compra) {
		Compra compraSalva = compraRepository.findById(compra.getCompraId()).orElse(null);
		
		if(compraSalva == null) {
			return realizaCompra(compra);
		}
		
		LOG.info("Inicia reprocessamento da compra "+compraSalva.getId()+"!");
		
		List<PedidoEnderecoItemDTO> enderecoItens;
		
		switch(compraSalva.getSituacao()) {
		case RECEBIDO:
			enderecoItens = realizaPedido(compraSalva, compra);
			reservaEntrega(compraSalva, compra, enderecoItens);
			break;
		case PEDIDO_REALIZADO:
			enderecoItens = fornecedorClient.getEnderecoItens(compraSalva.getPedidoId());
			reservaEntrega(compraSalva, compra, enderecoItens);
			break;
		default:
			break;
		}
		
		LOG.info("Reprocessamento da compra "+compraSalva.getId()+" realizado com sucesso!");
		
		return compraSalva;
	}
	
	@HystrixCommand(threadPoolKey = "realizaCompraThreadPool", 
					commandProperties = {@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000")})
	public void cancelar(Long id) {
		Compra compraSalva = compraRepository.findById(id).orElse(null);
		
		if(compraSalva != null) {
			LOG.info("Inicia cancelamento da compra "+compraSalva.getId()+"!");
			
			switch(compraSalva.getSituacao()) {
			case RECEBIDO:
				compraRepository.delete(compraSalva);
				break;
			case PEDIDO_REALIZADO:
				compraRepository.delete(compraSalva);
				desfazPedido(compraSalva.getPedidoId());
				break;
			case RESERVA_ENTREGA_REALIZADA:
				compraRepository.delete(compraSalva);
				desfazPedido(compraSalva.getPedidoId());
				desfazReservaEntrega(compraSalva.getVoucher());
				break;
			}
			
			LOG.info("Cancelamento da compra "+compraSalva.getId()+" realizado com sucesso!");
		}
	}
	
	@HystrixCommand(threadPoolKey = "realizaCompraThreadPool", 
					fallbackMethod = "realizaCompraFallback", 
					commandProperties = {@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000")})
	public Compra realizaCompra(CompraDTO compra) {
		inicio = System.currentTimeMillis();
		
		LOG.info("Inicia compra!");
		
		Compra compraSalva = new Compra();
		compraSalva.setEnderecoDestino(compra.getEndereco().toString());
		compraSalva.setSituacao(CompraSituacao.RECEBIDO);
		compraRepository.save(compraSalva);
		compra.setCompraId(compraSalva.getId());
		
		List<PedidoEnderecoItemDTO> enderecoItens = realizaPedido(compraSalva, compra);
		
		reservaEntrega(compraSalva, compra, enderecoItens);
		
		LOG.info("Compra realizada com sucesso! Tempo de execução em segundos:"+((System.currentTimeMillis() - inicio) / 1000));
		return compraSalva;
	}
	
	private List<PedidoEnderecoItemDTO> realizaPedido(Compra compraSalva, CompraDTO compra) {	
		PedidoSaidaDTO pedido = fornecedorClient.realizaPedido(compra.getItens());
		compraSalva.setPedidoId(pedido.getId());
		compraSalva.setTempoDePreparo(pedido.getTempoDePreparo());
		compraSalva.setSituacao(CompraSituacao.PEDIDO_REALIZADO);
		compraRepository.save(compraSalva);
		
		return pedido.getEnderecoItens();
	}
	
	private void desfazPedido(Long id) {
		fornecedorClient.desfazPedido(id);
	}
	
	private void reservaEntrega(Compra compraSalva, CompraDTO compra, List<PedidoEnderecoItemDTO> enderecoItens) {
		EntregaDTO entrega = new EntregaDTO();
		entrega.setPedidoId(compraSalva.getPedidoId());
		entrega.setDataParaEntrega(LocalDate.now().plusDays(compraSalva.getTempoDePreparo()));
		entrega.setOrigens(enderecoItens.stream().map(item -> {
			EntregaOrigemDTO origem = new EntregaOrigemDTO();
			origem.setProdutoId(item.getProdutoId());
			origem.setEndereco(item.getEndereco());
			return origem;
		}).collect(Collectors.toList()));
		entrega.setEnderecoDestino(compra.getEndereco().toString());
		
		VoucherDTO voucher = transportadorClient.reservaEntrega(entrega);
		compraSalva.setDataParaEntrega(voucher.getPrevisaoParaEntrega());
		compraSalva.setVoucher(voucher.getNumero());
		compraSalva.setSituacao(CompraSituacao.RESERVA_ENTREGA_REALIZADA);
		compraRepository.save(compraSalva);
	}
		
	private void desfazReservaEntrega(Long id) {
		transportadorClient.desfazReservaEntrega(id);
	}

	public  Compra realizaCompraFallback(CompraDTO compra) {
		if(compra.getCompraId() != null) {
			LOG.info("Tempo de execução em segundos:"+((System.currentTimeMillis() - inicio) / 1000));	
			return compraRepository.findById(compra.getCompraId()).get();
		}
		
		LOG.info("Tempo de execução em segundos:"+((System.currentTimeMillis() - inicio) / 1000));
		Compra compraFallback = new Compra();
		compraFallback.setEnderecoDestino(compra.getEndereco().toString());
		return compraFallback;
	}
}
