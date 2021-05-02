package fornecedor.fornecedor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fornecedor.fornecedor.modelo.Fornecedor;

@RestController
@RequestMapping("/")
public class FornecedorController {
	
	@Autowired
	private FornecedorService service;
	
	@GetMapping("/{estado}")
	public Fornecedor getInfo(@PathVariable("estado") String estado) {
		return service.getInfo(estado);
	}
}
