package loja.compra;

import org.springframework.data.jpa.repository.JpaRepository;

import loja.compra.modelo.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long>{

}
