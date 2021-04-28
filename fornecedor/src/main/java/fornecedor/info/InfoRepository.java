package fornecedor.info;

import org.springframework.data.jpa.repository.JpaRepository;

import fornecedor.info.modelo.InfoFornecedor;

public interface InfoRepository extends JpaRepository<InfoFornecedor, Long>{

	InfoFornecedor findByEstado(String estado);

}
