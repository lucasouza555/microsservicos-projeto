package loja.compra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import loja.compra.dto.CompraDTO;
import loja.compra.modelo.Compra;

@RestController
@RequestMapping("compras")
public class CompraController {
	
	@Autowired
	private CompraService service;
	
	@GetMapping("{id}")
	public Compra getCompra(@PathVariable("id") Long id) {
		return service.getCompra(id);
	}
	
	@PostMapping()
	public Compra realizaCompra(@RequestBody CompraDTO dto) {
		return service.realizaCompra(dto);
	}
}

