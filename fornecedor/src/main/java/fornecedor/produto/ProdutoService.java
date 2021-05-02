package fornecedor.produto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fornecedor.produto.modelo.Produto;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public List<Produto> getTodosProdutos() {
		return (List<Produto>)produtoRepository.findAll();
	}
}
