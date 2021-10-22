package br.com.rd.projetoVelhoLuxo.repository;

import br.com.rd.projetoVelhoLuxo.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsREPO extends JpaRepository<Products, Long> {
}
