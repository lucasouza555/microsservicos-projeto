package fornecedor.produto;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import fornecedor.produto.modelo.Produto;


public interface ProdutoRepository extends CrudRepository<Produto, Long>{

	List<Produto> findByEstado(String estado);
	
	List<Produto> findByIdIn(List<Long> ids);
}
