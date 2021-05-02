package fornecedor.produto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fornecedor.produto.modelo.Produto;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;
	
	@GetMapping()
	public List<Produto> getTodosProdutos() {
		return produtoService.getTodosProdutos();
	}
}
