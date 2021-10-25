package br.com.rd.projetoVelhoLuxo.repository.contract;


import br.com.rd.projetoVelhoLuxo.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryREPO extends JpaRepository<Category, Long> {
}
