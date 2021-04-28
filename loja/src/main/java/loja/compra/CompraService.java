package loja.compra;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import loja.client.fornecedor.FornecedorClient;
import loja.client.fornecedor.dto.FornecedorInfoDTO;
import loja.client.transportador.TransportadorClient;
import loja.client.transportador.dto.EntregaDTO;
import loja.client.transportador.dto.VoucherDTO;
import loja.compra.dto.CompraDTO;
import loja.compra.modelo.Compra;
import loja.compra.modelo.CompraSituacao;
import loja.pedido.dto.PedidoInfoDTO;

@Service
public class CompraService {
	
	private static final Logger LOG = LoggerFactory.getLogger(CompraService.class);
	
//	@Autowired
//	private RestTemplate client;
	
	@Autowired
	private FornecedorClient fornecedorClient;
	
	@Autowired
	private CompraRepository compraRepository;
		
	@Autowired
	private TransportadorClient transportadorClient;
	
	public void reprocessarCompra(Long id) {
		
	}
	
	public void cancelarCompra(Long id) {
		
	}
	
	@HystrixCommand(threadPoolKey = "getCompraThreadPool")
	public Compra getCompra(Long id) {
		return compraRepository.findById(id).orElse(new Compra());
	}
	
	@HystrixCommand(threadPoolKey = "realizaCompraThreadPool", fallbackMethod = "realizaCompraFallback")
	public Compra realizaCompra(CompraDTO compra) {
		LOG.info("Inicia compra!");
		
		Compra compraSalva = new Compra();
		compraSalva.setEnderecoDestino(compra.getEndereco().toString());
		compraSalva.setSituacao(CompraSituacao.RECEBIDO);
		compraRepository.save(compraSalva);
		compra.setCompraId(compraSalva.getId());
		
		FornecedorInfoDTO fornecedor = fornecedorClient.getInfo(compra.getEndereco().getEstado());
		
		PedidoInfoDTO pedido = fornecedorClient.realizaPedido(compra.getItens());
		compraSalva.setPedidoId(pedido.getId());
		compraSalva.setTempoDePreparo(pedido.getTempoDePreparo());
		compraSalva.setSituacao(CompraSituacao.PEDIDO_REALIZADO);
		compraRepository.save(compraSalva);
		
		EntregaDTO entrega = new EntregaDTO();
		entrega.setPedidoId(pedido.getId());
		entrega.setDataParaEntrega(LocalDate.now().plusDays(pedido.getTempoDePreparo()));
		entrega.setEnderecoOrigem(fornecedor.getEndereco());
		entrega.setEnderecoDestino(compra.getEndereco().toString());
		
		VoucherDTO voucher = transportadorClient.reservaEntrega(entrega);
		compraSalva.setDataParaEntrega(voucher.getPrevisaoParaEntrega());
		compraSalva.setVoucher(voucher.getNumero());
		compraSalva.setSituacao(CompraSituacao.RESERVA_ENTREGA_REALIZADA);
		compraRepository.save(compraSalva);
		
		LOG.info("Compra realizada com sucesso!");
		
		return compraSalva;
	}
	
	public  Compra realizaCompraFallback(CompraDTO compra) {
		if(compra.getCompraId() != null) {
			return compraRepository.findById(compra.getCompraId()).get();
		}
			
		Compra compraFallback = new Compra();
		compraFallback.setEnderecoDestino(compra.getEndereco().toString());
		return compraFallback;
	}
}