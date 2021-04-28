package fornecedor.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fornecedor.info.dto.InfoSaidaDTO;
import fornecedor.info.modelo.InfoFornecedor;

@RestController
@RequestMapping("/info")
public class InfoController {
	
	private static final Logger LOG = LoggerFactory.getLogger(InfoController.class);

	@Autowired
	private InfoService service;
	
	@GetMapping("/{estado}")
	public InfoSaidaDTO getInfo(@PathVariable("estado") String estado) {
		LOG.info("Carregando o estado");
		InfoFornecedor info = service.getInfo(estado);
		
		InfoSaidaDTO dto = null;
		
		if(info != null) {
			dto = InfoConverter.toDto(info);
		}
		
		return dto;
	}
}
