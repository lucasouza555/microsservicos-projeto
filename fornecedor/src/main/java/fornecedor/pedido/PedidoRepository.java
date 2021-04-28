package fornecedor.pedido;

import org.springframework.data.repository.CrudRepository;

import fornecedor.pedido.modelo.Pedido;

public interface PedidoRepository extends CrudRepository<Pedido, Long>{

}
