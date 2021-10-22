package br.com.rd.projetoVelhoLuxo.repository;


import br.com.rd.projetoVelhoLuxo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryREPO extends JpaRepository<Category, Long> {
}
