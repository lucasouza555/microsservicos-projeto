package transportador.entrega;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import transportador.entrega.modelo.Entrega;

@Repository
public interface EntregaRepository extends CrudRepository<Entrega, Long>{

}
