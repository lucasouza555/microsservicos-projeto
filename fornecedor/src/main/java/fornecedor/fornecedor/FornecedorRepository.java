package fornecedor.fornecedor;

import org.springframework.data.jpa.repository.JpaRepository;

import fornecedor.fornecedor.modelo.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long>{

	Fornecedor findByEstado(String estado);

}
