package fornecedor.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fornecedor.info.modelo.InfoFornecedor;

@Service
public class InfoService {
	
	@Autowired
	private InfoRepository repository;
	
	public InfoFornecedor getInfo(String estado) {
		return repository.findByEstado(estado);
	}

}
