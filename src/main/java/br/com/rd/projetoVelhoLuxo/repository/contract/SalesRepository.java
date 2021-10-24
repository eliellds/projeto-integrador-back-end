package br.com.rd.projetoVelhoLuxo.repository.contract;

import br.com.rd.projetoVelhoLuxo.model.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepository  extends JpaRepository<Sales, Long> {
}
