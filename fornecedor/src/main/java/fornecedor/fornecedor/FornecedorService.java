package fornecedor.fornecedor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fornecedor.fornecedor.modelo.Fornecedor;

@Service
public class FornecedorService {
	
	private static final Logger LOG = LoggerFactory.getLogger(FornecedorService.class);
	
	@Autowired
	private FornecedorRepository repository;
	
	public Fornecedor getInfo(String estado) {
		LOG.info("Carregando o estado");
		return repository.findByEstado(estado);
	}

}
